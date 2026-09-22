package com.tp.taskflow.feature.product.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class StudioProduct(
    val id: String,
    val name: String,
    val price: String,
    val description: String,
    val onSale: Boolean = true
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val product: StudioProduct = studioProduct(savedStateHandle.get<String>("id").orEmpty())

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
