package com.tp.taskflow.navigation

object Routes {
    const val Login = "login"
    const val Register = "register"
    const val Home = "home"
    const val Search = "search"
    const val Notes = "notes"
    const val Profile = "profile"
    const val Settings = "settings"
    const val Chat = "chat"
    const val ProductDetail = "product/{id}"

    fun product(id: String) = "product/$id"
}
