package com.umbrella.vkapiapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentPhotosBinding
import com.umbrella.vkapiapp.domain.entity.Photo
import com.umbrella.vkapiapp.presentation.adapters.PhotosAdapter
import com.umbrella.vkapiapp.presentation.mapper.toPresentationModel
import com.umbrella.vkapiapp.presentation.model.PhotoPresentationModel
import com.umbrella.vkapiapp.presentation.utils.*
import com.umbrella.vkapiapp.presentation.viewmodels.PhotosViewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.createScope
import org.koin.core.component.inject
import org.koin.core.scope.Scope

class PhotosFragment : Fragment(), KoinScopeComponent {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private val photosAdapter by lazy {
        PhotosAdapter()
    }
    override val scope: Scope by lazy {
        createScope(this)
    }
    private val viewModel: PhotosViewModel by inject()
    private val args by navArgs<PhotosFragmentArgs>()

    private var positionOfPhotoWhereDownloadStart = 0
    private var isAllPhotosDownloadedFromAlbum = false

    companion object {
        private const val MIN_COLUMN_COUNT = 3
        private const val DOWNLOADING_PHOTOS_COUNT = 50
        private const val SCROLL_DIRECTION_DOWN = 1
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
            navigateToPhotoDetailFragment(it.toPresentationModel())
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

    private fun navigateToPhotoDetailFragment(photo: PhotoPresentationModel) {
        findNavController().navigate(
            PhotosFragmentDirections.actionNavigateToPhotoDetailFragment(photo)
        )
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
            args.albumId, positionOfPhotoWhereDownloadStart, DOWNLOADING_PHOTOS_COUNT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }
}