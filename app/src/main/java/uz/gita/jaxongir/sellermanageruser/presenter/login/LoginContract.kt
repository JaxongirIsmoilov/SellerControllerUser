package uz.gita.jaxongir.sellermanageruser.presenter.login

import android.content.Context
import kotlinx.coroutines.flow.StateFlow

interface LoginContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val loading: Boolean = false,
    )

    interface Intent {
        data class OnLogin(val sellerName: String, val password: String, val context: Context) :
            Intent

        data class ProgressState(val state: Boolean) : Intent
    }
}