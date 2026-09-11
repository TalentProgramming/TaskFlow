package com.tp.taskflow.feature.product.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tp.taskflow.core.common.Resource
import com.tp.taskflow.feature.product.domain.Product
import com.tp.taskflow.feature.product.domain.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    searchProducts: SearchProductsUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val uiState: StateFlow<Resource<List<Product>>> = _query
        .debounce(SEARCH_DEBOUNCE_MS)
        .distinctUntilChanged()
        .flatMapLatest { text -> searchProducts(text) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resource.Empty
        )

    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow()

    fun onQueryChange(value: String) {
        _query.value = value
    }

    fun onProductClick(product: Product) {
        viewModelScope.launch {
            _events.emit("Opened ${product.name}")
        }
    }

    private companion object {
        const val SEARCH_DEBOUNCE_MS = 500L
    }
}
