package com.umbrella.vkapiapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentAuthorizationBinding
import com.umbrella.vkapiapp.presentation.viewmodels.AuthorizationViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorizationViewModel by viewModel()

    companion object {
        fun newInstance(): AuthorizationFragment {
            return AuthorizationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tokenLiveData.observe(viewLifecycleOwner) { token ->
            if (token == null) {
                VK.login(requireActivity(), arrayListOf(VKScope.PHOTOS))
            } else {
                navigateToAlbumsFragment()
            }
        }

        viewModel.getToken()
    }

    private fun navigateToAlbumsFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, AlbumsFragment.newInstance())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}