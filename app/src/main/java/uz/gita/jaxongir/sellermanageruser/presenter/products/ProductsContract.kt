package uz.gita.jaxongir.sellermanageruser.presenter.products

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.sellermanageruser.data.models.ProductData

interface ProductsContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val ls: List<ProductData> = emptyList(),
    )

    interface Intent {
        data class ProductsData(
            val id: String,
            val name: String,
            var count: Int,
            val initialPrice: Double,
            val soldPrice: Double,
            val isValid: Boolean,
            val comment: String,
            val sellerName: String,
            val adminToken: String
        ) : Intent

        object Logout : Intent
    }
}