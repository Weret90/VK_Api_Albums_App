package com.umbrella.vkapiapp.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
        findNavController().navigate(R.id.action_navigate_to_albums_fragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.refresh_authorization_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.getToken()
        return super.onOptionsItemSelected(item)
    }
}