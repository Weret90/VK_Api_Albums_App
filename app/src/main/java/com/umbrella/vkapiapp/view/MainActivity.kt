package com.umbrella.vkapiapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.umbrella.vkapiapp.R
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback


class MainActivity : AppCompatActivity() {

    companion object {
        const val ARG_TOKEN = "token"
    }

    private val authorizationTextView: TextView by lazy {
        findViewById(R.id.authorizationTextView)
    }
    private val authorizationButton: Button by lazy {
        findViewById(R.id.authorizationButton)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                setOnLoginSuccessInfoToUser()
                authorizationButton.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString(ARG_TOKEN, token.accessToken)
                    findNavController(R.id.fragmentContainerView).navigate(
                        R.id.listOfPhotoAlbumsFragment,
                        bundle
                    )
                }
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.access_is_denied),
                    Toast.LENGTH_SHORT
                ).show()
                setLoginFailedInfoToUser()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setOnLoginSuccessInfoToUser() {
        authorizationTextView.text = getString(R.string.authorization_text_view_access_success_text)
        authorizationTextView.visibility = View.VISIBLE
        authorizationButton.text = getString(R.string.authorization_button_start_watching_text)
        authorizationButton.visibility = View.VISIBLE
    }

    private fun setLoginFailedInfoToUser() {
        authorizationTextView.text = getString(R.string.authorization_text_view_access_denied_text)
        authorizationTextView.visibility = View.VISIBLE
        authorizationButton.text = getString(R.string.authorization_button_try_again_text)
        authorizationButton.visibility = View.VISIBLE
    }
}

