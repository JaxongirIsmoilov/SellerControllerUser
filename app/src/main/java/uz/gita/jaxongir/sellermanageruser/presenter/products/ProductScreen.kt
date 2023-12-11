package uz.gita.jaxongir.sellermanageruser.presenter.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.sellermanageruser.R
import uz.gita.jaxongir.sellermanageruser.ui.components.ProductsComponent
import uz.gita.jaxongir.sellermanageruser.ui.components.SimpleSearchItem

class ProductScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val viewModel = getViewModel<ProductsViewModel>()
        ProductsScreenContent(
            uiState = viewModel.uiState.collectAsState(),
            onEventDispatcher = viewModel::onEventDispatcher
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreenContent(
    uiState: State<ProductsContract.UIState>,
    onEventDispatcher: (ProductsContract.Intent) -> Unit,
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Search...",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFADADAD),
                    )
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        onEventDispatcher.invoke(
                            ProductsContract.Intent.Logout
                        )
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(Color(0xFF673AB7))
        )

        SimpleSearchItem(
            modifier = Modifier.fillMaxWidth(),
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = "Search..."
        )

        val filteredList = uiState.value.ls.filter {
            it.name.contains(searchText, ignoreCase = true)
        }

        var sellingPrice by remember {
            mutableStateOf("")
        }

        LazyColumn {
            items(filteredList) {
                if (it.count > 0) {
                    ProductsComponent(
                        productCommon = it,
                        onClickSell = {
                            onEventDispatcher(
                                ProductsContract.Intent.ProductsData(
                                    it.id,
                                    it.name,
                                    it.count,
                                    it.initialPrice,
                                    sellingPrice.toDouble(),
                                    it.isValid,
                                    it.comment,
                                    it.sellerName,
                                    it.adminToken
                                )
                            )
                        },
                        sellingPrice = sellingPrice,
                        onSellingPriceChange = { sellingPrice = it }
                    )
                }
            }
        }
    }
}