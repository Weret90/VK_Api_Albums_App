package com.umbrella.vkapiapp.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.ActivityMainBinding
import com.umbrella.vkapiapp.presentation.utils.showSnackBar
import com.umbrella.vkapiapp.presentation.utils.showToast
import com.umbrella.vkapiapp.presentation.viewmodels.AuthorizationViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.exceptions.VKAuthException
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: AuthorizationViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTokenExpiredHandler()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                viewModel.saveToken(token.accessToken)
                navigateToAlbumsFragment()
            }

            override fun onLoginFailed(authException: VKAuthException) {
                binding.root.showSnackBar(
                    authException.message,
                    getString(R.string.snack_bar_action_text)
                ) {
                    VK.login(this@MainActivity, arrayListOf(VKScope.PHOTOS))
                }
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initTokenExpiredHandler() {
        val tokenTracker = object : VKTokenExpiredHandler {
            override fun onTokenExpired() {
                viewModel.clearToken()
                this@MainActivity.showToast(getString(R.string.token_expired))
                navigateToAuthorizationFragment()
            }
        }
        VK.addTokenExpiredHandler(tokenTracker)
    }

    private fun navigateToAlbumsFragment() {
        Navigation.findNavController(this, R.id.main_container)
            .navigate(R.id.action_navigate_to_albums_fragment)
    }

    private fun navigateToAuthorizationFragment() {
        Navigation.findNavController(this, R.id.main_container)
            .navigate(R.id.authorizationFragment)
    }
}