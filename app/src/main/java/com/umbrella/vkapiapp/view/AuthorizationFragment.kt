package com.umbrella.vkapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umbrella.vkapiapp.databinding.FragmentAuthorizationBinding
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.authorizationButton.setOnClickListener {
            VK.login(requireActivity(), arrayListOf(VKScope.PHOTOS))
            binding.authorizationButton.visibility = View.GONE
            binding.authorizationTextView.visibility = View.GONE
        }
    }
}