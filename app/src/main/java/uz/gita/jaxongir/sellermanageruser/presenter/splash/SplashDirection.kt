package uz.gita.jaxongir.sellermanageruser.presenter.splash

import uz.gita.jaxongir.sellermanageruser.presenter.login.LoginScreen
import uz.gita.jaxongir.sellermanageruser.presenter.products.ProductScreen
import uz.gita.jaxongir.sellermanageruser.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface SplashDirection {
    suspend fun moveToLogin()
    suspend fun moveToProductScreen()
}


@Singleton
class SplashDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SplashDirection {
    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

    override suspend fun moveToProductScreen() {
        appNavigator.addScreen(ProductScreen())
    }
}