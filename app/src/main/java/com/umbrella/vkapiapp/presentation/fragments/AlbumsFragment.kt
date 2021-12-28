package com.umbrella.vkapiapp.presentation.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentAlbumsBinding
import com.umbrella.vkapiapp.domain.entity.Album
import com.umbrella.vkapiapp.presentation.viewmodels.AlbumsViewModel
import com.umbrella.vkapiapp.presentation.adapters.AlbumsAdapter
import com.umbrella.vkapiapp.presentation.utils.*
import com.vk.api.sdk.VK
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumsViewModel by viewModel()
    private val albumsAdapter by lazy {
        AlbumsAdapter()
    }

    companion object {
        private const val MIN_COLUMN_COUNT = 2

        fun newInstance(): AlbumsFragment {
            return AlbumsFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.albumsRecyclerView.layoutManager = GridLayoutManager(
            context, getColumnCount(activity, MIN_COLUMN_COUNT)
        )
        binding.albumsRecyclerView.adapter = albumsAdapter
        albumsAdapter.onAlbumClickListener = { album ->
            navigateToPhotosFragment(album.id.toString())
        }

        viewModel.albumsLiveData.observe(viewLifecycleOwner) { loadAlbumsState ->
            loadAlbumsState?.let {
                renderState(it)
            }
        }

        if (albumsAdapter.getAlbums().isEmpty()) {
            viewModel.getAlbums()
        }
    }

    private fun renderState(state: State<List<Album>>) {
        with(binding) {
            when (state) {
                is State.Loading -> {
                    loadingBar.show()
                }
                is State.Success -> {
                    loadingBar.hide()
                    albumsAdapter.setAlbums(state.data)
                }
                is State.Error -> {
                    loadingBar.hide()
                    binding.root.showSnackBar(
                        state.error.message, getString(R.string.snack_bar_action_text)
                    ) {
                        viewModel.getAlbums()
                    }
                }
            }
        }
        viewModel.clearAlbumsLiveData()
    }

    private fun navigateToPhotosFragment(albumId: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, PhotosFragment.newInstance(albumId))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.exit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.clearToken()
        VK.logout()
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_container, AuthorizationFragment.newInstance())
            .commit()
        return super.onOptionsItemSelected(item)
    }
}