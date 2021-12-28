package com.umbrella.vkapiapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentPhotosBinding
import com.umbrella.vkapiapp.domain.entity.Photo
import com.umbrella.vkapiapp.presentation.adapters.PhotosAdapter
import com.umbrella.vkapiapp.presentation.mapper.toPresentationModel
import com.umbrella.vkapiapp.presentation.utils.*
import com.umbrella.vkapiapp.presentation.viewmodels.PhotosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PhotosViewModel by viewModel()
    private val photosAdapter by lazy {
        PhotosAdapter()
    }
    private val albumId: String by lazy {
        requireArguments().getString(KEY_ALBUM_ID, "")
    }
    private var positionOfPhotoWhereDownloadStart = 0
    private var isAllPhotosDownloadedFromAlbum = false

    companion object {
        private const val KEY_ALBUM_ID = "albumId"
        private const val MIN_COLUMN_COUNT = 3
        private const val DOWNLOADING_PHOTOS_COUNT = 50
        private const val SCROLL_DIRECTION_DOWN = 1

        fun newInstance(albumId: String): PhotosFragment {
            return PhotosFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ALBUM_ID, albumId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.photosRecyclerView.adapter = photosAdapter
        binding.photosRecyclerView.layoutManager = GridLayoutManager(
            context, getColumnCount(activity, MIN_COLUMN_COUNT)
        )

        viewModel.photosLiveData.observe(viewLifecycleOwner) { loadPhotosState ->
            loadPhotosState?.let {
                renderState(it)
            }
        }

        photosAdapter.onPhotoClickListener = {
            navigateToPhotoDetailFragment(it)
        }

        binding.photosRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(SCROLL_DIRECTION_DOWN)) {
                    if (!isAllPhotosDownloadedFromAlbum) {
                        downloadPhotos()
                    }
                }
            }
        })

        if (photosAdapter.getPhotos().isEmpty()) {
            downloadPhotos()
        }
    }

    private fun navigateToPhotoDetailFragment(photo: Photo) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.main_container, PhotoDetailFragment.newInstance(photo.toPresentationModel())
            )
            .addToBackStack(null)
            .commit()
    }

    private fun renderState(state: State<List<Photo>>) {
        when (state) {
            is State.Loading -> {
                binding.loadingBar.show()
            }
            is State.Success -> {
                binding.loadingBar.hide()
                if (state.data.isEmpty()) {
                    isAllPhotosDownloadedFromAlbum = true
                } else {
                    positionOfPhotoWhereDownloadStart += DOWNLOADING_PHOTOS_COUNT
                    photosAdapter.addPhotos(state.data)
                }
            }
            is State.Error -> {
                binding.loadingBar.hide()
                binding.root.showSnackBar(
                    state.error.message, getString(R.string.snack_bar_action_text)
                ) {
                    downloadPhotos()
                }
            }
        }
        viewModel.clearPhotosLiveData()
    }

    private fun downloadPhotos() {
        viewModel.getPhotosFromAlbum(
            albumId, positionOfPhotoWhereDownloadStart, DOWNLOADING_PHOTOS_COUNT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}