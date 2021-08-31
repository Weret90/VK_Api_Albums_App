package com.umbrella.vkapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.umbrella.vkapiapp.databinding.FragmentListOfPhotoAlbumsBinding

class ListOfPhotoAlbumsFragment : Fragment() {

    private var _binding: FragmentListOfPhotoAlbumsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfPhotoAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}