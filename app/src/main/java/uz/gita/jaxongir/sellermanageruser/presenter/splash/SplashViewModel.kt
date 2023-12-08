package uz.gita.jaxongir.sellermanageruser.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.jaxongir.sellermanageruser.data.source.local.pref.MyPref
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val direction: SplashDirection,
    private val myPref: MyPref
): ViewModel() {
    init {

        viewModelScope.launch {
            if (myPref.isLogin()) {
                delay(1000L)
                direction.moveToProductScreen()
            } else {
                delay(1500L)
                direction.moveToLogin()
            }
        }

    }
}