package com.umbrella.vkapiapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentPhotoListBinding
import com.umbrella.vkapiapp.model.AppState
import com.umbrella.vkapiapp.view.ListOfPhotoAlbumsFragment.Companion.ARG_ID_ALBUM
import com.umbrella.vkapiapp.view.ListOfPhotoAlbumsFragment.Companion.getColumnCount
import com.umbrella.vkapiapp.view.MainActivity.Companion.ARG_TOKEN
import com.umbrella.vkapiapp.view.adapters.PhotosAdapter
import com.umbrella.vkapiapp.viewmodel.MainViewModel
import kotlin.properties.Delegates

class PhotoListFragment : Fragment() {

    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val photosAdapter = PhotosAdapter()
    private var token by Delegates.notNull<String>()
    private var albumId by Delegates.notNull<Int>()
    private var positionOfPhotoWhereDownloadStart = 0
    private var isButtonLoadMorePhotosVisible = false

    companion object {
        const val ARG_PHOTO_URL = "photoUrl"
        const val ARG_PHOTO_COMMENTS = "comments"
        const val ARG_PHOTO_REPOSTS = "reposts"
        const val ARG_PHOTO_LIKES = "likes"
        private const val MIN_COLUMN_COUNT = 3
        private const val PHOTOS_IN_ONE_API_CALL = 50
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            albumId = it.getInt(ARG_ID_ALBUM)
            token = it.getString(ARG_TOKEN, "")

            binding.photosRecyclerView.adapter = photosAdapter
            binding.photosRecyclerView.layoutManager =
                GridLayoutManager(context, getColumnCount(activity, MIN_COLUMN_COUNT))

            binding.buttonLoadMorePhotos.setOnClickListener {
                positionOfPhotoWhereDownloadStart += PHOTOS_IN_ONE_API_CALL
                viewModel.makeApiCallGetPhotos(
                    token,
                    albumId.toString(),
                    positionOfPhotoWhereDownloadStart.toString()
                )
            }

            if (isButtonLoadMorePhotosVisible) {
                binding.buttonLoadMorePhotos.visibility = View.VISIBLE
            }

            photosAdapter.setOnPhotoClickListener { photo ->
                val bundle = Bundle()
                bundle.putString(ARG_PHOTO_URL, photo.sizes[photo.sizes.size - 1].url)
                bundle.putInt(ARG_PHOTO_COMMENTS, photo.comments.count)
                bundle.putInt(ARG_PHOTO_LIKES, photo.likes.count)
                bundle.putInt(ARG_PHOTO_REPOSTS, photo.reposts.count)
                findNavController().navigate(R.id.photoDetailFragment, bundle)
            }

            initMainObserver()

            if (photosAdapter.getPhotos().isEmpty()) {
                positionOfPhotoWhereDownloadStart = 0
                viewModel.makeApiCallGetPhotos(
                    token,
                    albumId.toString(),
                    positionOfPhotoWhereDownloadStart.toString()
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMainObserver() {
        viewModel.photosLiveData.observe(viewLifecycleOwner) { result ->
            when (result) {
                is AppState.Loading -> {
                    binding.loadingLayout.root.visibility = View.VISIBLE
                    binding.buttonLoadMorePhotos.visibility = View.GONE
                    isButtonLoadMorePhotosVisible = false
                }
                is AppState.SuccessGetPhotos -> {
                    binding.loadingLayout.root.visibility = View.GONE
                    photosAdapter.addPhotos(result.response.photos)
                    if (result.response.totalNumOfPhotosInAlbum > PHOTOS_IN_ONE_API_CALL && positionOfPhotoWhereDownloadStart < result.response.totalNumOfPhotosInAlbum) {
                        binding.buttonLoadMorePhotos.visibility = View.VISIBLE
                        isButtonLoadMorePhotosVisible = true
                    }
                    viewModel.photosLiveData.value = null
                }
                is AppState.Error -> {
                    binding.loadingLayout.root.visibility = View.GONE
                    Snackbar.make(
                        binding.root, String.format(
                            getString(R.string.app_state_error_text),
                            result.throwable.toString()
                        ), Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.snackbar_reload)) {
                        positionOfPhotoWhereDownloadStart = 0
                        viewModel.makeApiCallGetPhotos(
                            token,
                            albumId.toString(),
                            positionOfPhotoWhereDownloadStart.toString()
                        )
                    }.show()
                }
            }
        }
    }
}