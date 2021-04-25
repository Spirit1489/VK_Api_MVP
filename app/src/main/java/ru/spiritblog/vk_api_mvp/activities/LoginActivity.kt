package ru.spiritblog.vk_api_mvp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils.getCertificateFingerprint
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import ru.spiritblog.vk_api_mvp.R
import ru.spiritblog.vk_api_mvp.presenters.LoginPresenter
import ru.spiritblog.vk_api_mvp.views.LoginView


private const val TAG = "LoginActivity"


class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var txtHello: TextView
    private lateinit var btn: Button
    private lateinit var progressBar: ProgressBar

    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        txtHello = findViewById(R.id.textView)
        btn = findViewById(R.id.buttonMainScreen)
        progressBar = findViewById(R.id.progressBar)


        btn.setOnClickListener {
            VK.login(this, arrayListOf(VKScope.FRIENDS, VKScope.PHOTOS))
        }
//        val fingerprints = "D30632348E6B598A653025BD97D78CA4DDBE32B4"
//        Log.d(TAG, "fingerprints ${fingerprints?.get(0)}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        if (!loginPresenter.loginVk(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }


    }

    override fun startLoading() {
        btn.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun endLoading() {
        btn.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showError(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    override fun openFriends() {
        startActivity(Intent(applicationContext, FriendsActivity::class.java))
    }
}