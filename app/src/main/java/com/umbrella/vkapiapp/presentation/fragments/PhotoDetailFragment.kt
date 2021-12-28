package com.umbrella.vkapiapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentPhotoDetailBinding
import com.umbrella.vkapiapp.presentation.model.PhotoPresentationModel
import com.umbrella.vkapiapp.presentation.utils.hide
import com.umbrella.vkapiapp.presentation.utils.show
import com.umbrella.vkapiapp.presentation.utils.showSnackBar

class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PhotoDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photo = args.photo

        downloadPhoto(photo)

        with(binding) {
            likes.text = photo.likes.count.toString()
            reposts.text = photo.reposts.count.toString()
            comments.text = photo.comments.count.toString()
        }
    }

    private fun downloadPhoto(photo: PhotoPresentationModel) {
        binding.loadingBar.show()
        Picasso.get().load(photo.sizes.last().url).into(binding.bigPhoto, object : Callback {
            override fun onSuccess() {
                _binding?.photoInfoLayout?.show()
                _binding?.loadingBar?.hide()
            }

            override fun onError(e: Exception?) {
                _binding?.loadingBar?.hide()
                _binding?.root?.showSnackBar(
                    e?.message, getString(R.string.snack_bar_action_text)
                ) {
                    downloadPhoto(photo)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}