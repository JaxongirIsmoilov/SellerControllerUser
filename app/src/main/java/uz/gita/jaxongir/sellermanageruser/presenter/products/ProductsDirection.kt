package uz.gita.jaxongir.sellermanageruser.presenter.products

import uz.gita.jaxongir.sellermanageruser.presenter.login.LoginScreen
import uz.gita.jaxongir.sellermanageruser.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface ProductsDirection {
    suspend fun backToLogin()
}

@Singleton
class ProductsDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : ProductsDirection{
    override suspend fun backToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

}