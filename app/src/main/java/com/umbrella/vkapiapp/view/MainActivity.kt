package com.umbrella.vkapiapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.umbrella.vkapiapp.R
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VK.login(this, arrayListOf(VKScope.PHOTOS, VKScope.WALL))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                Toast.makeText(applicationContext, getString(R.string.access_is_allowed), Toast.LENGTH_LONG).show()
            }

            override fun onLoginFailed(errorCode: Int) {
                Toast.makeText(applicationContext, getString(R.string.access_is_denied), Toast.LENGTH_LONG).show()
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

