package uz.gita.jaxongir.sellermanageruser.presenter.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.sellermanageruser.data.models.ProductData
import uz.gita.jaxongir.sellermanageruser.data.source.local.pref.MyPref
import uz.gita.jaxongir.sellermanageruser.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val myPref: MyPref,
    private val direction: ProductsDirection,
    private val repository: AppRepository
) : ViewModel(), ProductsContract.ViewModel {

    init {
        repository.getAllProducts().onEach {
            uiState.update { uiState ->
                uiState.copy(ls = it)
            }
        }.launchIn(viewModelScope)
    }

    override val uiState = MutableStateFlow(ProductsContract.UIState())
    override fun onEventDispatcher(intent: ProductsContract.Intent) {
        when (intent) {
            is ProductsContract.Intent.ProductsData -> {
                viewModelScope.launch {
                    repository.sellProduct(
                        ProductData(
                            intent.id,
                            intent.name,
                            intent.count,
                            intent.initialPrice,
                            intent.soldPrice,
                            false,
                            intent.comment,
                            myPref.getUserName(),
                            adminToken = intent.adminToken
                        )
                    )
                }
            }

            ProductsContract.Intent.Logout -> {
                viewModelScope.launch {
                    myPref.clearData()
                    direction.backToLogin()
                }
            }
        }
    }


}