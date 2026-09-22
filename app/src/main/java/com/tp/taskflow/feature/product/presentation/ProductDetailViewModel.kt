package com.tp.taskflow.feature.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudioProduct(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    val onSale: Boolean = true,
    val progress: Float = 0.65f
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val product: StudioProduct = studioProduct(savedStateHandle.get<String>("id").orEmpty())

    private val _favorite = MutableStateFlow(false)
    val favorite = _favorite.asStateFlow()

    private val _banner = MutableStateFlow(false)
    val banner = _banner.asStateFlow()

    fun toggleFavorite() {
        val next = !_favorite.value
        _favorite.value = next
        if (next) {
            _banner.value = true
            viewModelScope.launch {
                delay(2_000)
                _banner.value = false
            }
        }
    }

    companion object {
        fun studioProduct(id: String): StudioProduct {
            if (id.isBlank() || id == "studio-mug") {
                return StudioProduct(
                    id = "studio-mug",
                    name = "Studio Mug",
                    price = "MMK 12,000",
                    description = "Ceramic mug for the Compose studio week. Students layout this screen from the mockup."
                )
            }
            return StudioProduct(
                id = id,
                name = "Studio Mug",
                price = "MMK 12,000",
                description = "Ceramic mug for the Compose studio week. Catalog id: $id"
            )
        }
    }
}
