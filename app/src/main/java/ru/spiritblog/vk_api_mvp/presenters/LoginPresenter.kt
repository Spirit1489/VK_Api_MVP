package ru.spiritblog.vk_api_mvp.presenters

import android.content.Intent
import android.os.Handler
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.spiritblog.vk_api_mvp.R
import ru.spiritblog.vk_api_mvp.views.LoginView

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    fun login(isSuccess: Boolean) {
        viewState.startLoading()

        Handler().postDelayed({
            viewState.endLoading()

            if (isSuccess) {

                viewState.openFriends()


            } else
                viewState.showError(text = "Login data is incorrect")


        }, 500)

    }

    fun loginVk(requestCode: Int, resultCode: Int, data: Intent?): Boolean {


        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                viewState.openFriends()
            }

            override fun onLoginFailed(errorCode: Int) {
                viewState.showError("Login data is incorrect")
            }
        }

        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            return false
        }

        return true

    }
}