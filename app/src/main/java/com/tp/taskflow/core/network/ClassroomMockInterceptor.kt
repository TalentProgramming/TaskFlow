package com.tp.taskflow.core.network

import com.tp.taskflow.feature.auth.data.FakeAuthRepository
import com.tp.taskflow.feature.product.data.ClassroomProductCatalog
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class ClassroomMockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val json = "application/json".toMediaType()
        fun ok(body: String, code: Int = 200) = Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(code)
            .message("OK")
            .body(body.toResponseBody(json))
            .build()

        return when {
            path.endsWith("/auth/login") -> {
                val raw = request.body?.let { bufferBody(it) } ?: "{}"
                val parsed = JSONObject(raw)
                val email = parsed.optString("email")
                val password = parsed.optString("password")
                if (email == FakeAuthRepository.DEMO_EMAIL && password == FakeAuthRepository.DEMO_PASSWORD) {
                    ok(
                        """{"token":"tf-classroom-token","user":{"id":"u-1","name":"Aung Ko","email":"$email"}}"""
                    )
                } else {
                    ok("""{"message":"Invalid credentials"}""", 401)
                }
            }
            path.endsWith("/profile/me") && request.method == "GET" -> {
                if (request.header("Authorization").isNullOrBlank()) {
                    ok("""{"message":"Unauthorized"}""", 401)
                } else {
                    ok("""{"id":"u-1","name":"Aung Ko","email":"${FakeAuthRepository.DEMO_EMAIL}","photoUrl":null}""")
                }
            }
            path.endsWith("/profile/me") && request.method == "PUT" -> {
                ok(request.body?.let { bufferBody(it) } ?: """{"id":"u-1","name":"Aung Ko","email":"student@example.com"}""")
            }
            path.endsWith("/profile/me/photo") -> {
                ok("""{"id":"u-1","name":"Aung Ko","email":"${FakeAuthRepository.DEMO_EMAIL}","photoUrl":"mock://photo"}""")
            }
            path.endsWith("/products") -> {
                Thread.sleep(PAGE_DELAY_MS)
                val q = request.url.queryParameter("q").orEmpty()
                if (q.equals("error", ignoreCase = true)) {
                    ok("""{"message":"Search service unavailable"}""", 500)
                } else {
                    val page = request.url.queryParameter("page")?.toIntOrNull() ?: 1
                    ok(ClassroomProductCatalog.toJson(ClassroomProductCatalog.page(q, page)))
                }
            }
            else -> ok("""{"message":"Not found"}""", 404)
        }
    }

    private fun bufferBody(body: okhttp3.RequestBody): String {
        val buffer = okio.Buffer()
        body.writeTo(buffer)
        return buffer.readUtf8()
    }

    private companion object {
        const val PAGE_DELAY_MS = 800L
    }
}
