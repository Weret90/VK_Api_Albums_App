package com.umbrella.vkapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentPhotoDetailBinding
import com.umbrella.vkapiapp.view.PhotoListFragment.Companion.ARG_PHOTO_COMMENTS
import com.umbrella.vkapiapp.view.PhotoListFragment.Companion.ARG_PHOTO_LIKES
import com.umbrella.vkapiapp.view.PhotoListFragment.Companion.ARG_PHOTO_REPOSTS
import com.umbrella.vkapiapp.view.PhotoListFragment.Companion.ARG_PHOTO_URL
import java.lang.Exception

class PhotoDetailFragment : Fragment() {

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val photoUrl = it.getString(ARG_PHOTO_URL)
            Picasso.get().load(photoUrl).into(binding.bigPhoto, object : Callback {
                override fun onSuccess() {
                    binding.loadingLayout.root.visibility = View.GONE
                    binding.photoInfoLayout.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    binding.loadingLayout.root.visibility = View.GONE
                    Toast.makeText(
                        context,
                        String.format(getString(R.string.app_state_error_text, e.toString())),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
            binding.likes.text = it.getInt(ARG_PHOTO_LIKES).toString()
            binding.reposts.text = it.getInt(ARG_PHOTO_REPOSTS).toString()
            binding.comments.text = it.getInt(ARG_PHOTO_COMMENTS).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}