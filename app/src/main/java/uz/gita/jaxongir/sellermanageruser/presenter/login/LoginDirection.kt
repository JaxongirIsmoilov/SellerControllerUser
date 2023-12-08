package uz.gita.jaxongir.sellermanageruser.presenter.login

import uz.gita.jaxongir.sellermanageruser.presenter.products.ProductScreen
import uz.gita.jaxongir.sellermanageruser.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface LoginDirection {
    suspend fun moveToEntryScreen()
}

@Singleton
class LoginDirectionImpl @Inject constructor(
    val appNavigator: AppNavigator
) : LoginDirection {
    override suspend fun moveToEntryScreen() {
        appNavigator.replaceScreen(ProductScreen())
    }


}