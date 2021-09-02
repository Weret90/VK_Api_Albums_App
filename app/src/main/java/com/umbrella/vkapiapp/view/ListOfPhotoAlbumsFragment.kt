package com.umbrella.vkapiapp.view

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.umbrella.vkapiapp.R
import com.umbrella.vkapiapp.databinding.FragmentListOfPhotoAlbumsBinding
import com.umbrella.vkapiapp.model.AppState
import com.umbrella.vkapiapp.view.MainActivity.Companion.ARG_TOKEN
import com.umbrella.vkapiapp.view.adapters.AlbumsAdapter
import com.umbrella.vkapiapp.viewmodel.MainViewModel
import kotlin.properties.Delegates

class ListOfPhotoAlbumsFragment : Fragment() {

    private var _binding: FragmentListOfPhotoAlbumsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val albumsAdapter = AlbumsAdapter()
    private var token by Delegates.notNull<String>()

    companion object {
        private const val MIN_COLUMN_COUNT = 2
        const val ARG_ID_ALBUM = "albumId"

        fun getColumnCount(activity: Activity?, minColumnCount: Int): Int {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
            val width = displayMetrics.widthPixels / displayMetrics.density
            val columnCount = (width / 185).toInt()
            return if (columnCount > minColumnCount) {
                columnCount
            } else {
                minColumnCount
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfPhotoAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            token = it.getString(ARG_TOKEN, "")

            binding.albumsRecyclerView.layoutManager =
                GridLayoutManager(context, getColumnCount(activity, MIN_COLUMN_COUNT))
            binding.albumsRecyclerView.adapter = albumsAdapter

            albumsAdapter.setOnAlbumClickListener { album ->
                val bundle = Bundle()
                bundle.putInt(ARG_ID_ALBUM, album.id)
                bundle.putString(ARG_TOKEN, token)
                findNavController().navigate(R.id.photoListFragment, bundle)
            }

            initMainObserver()

            if (albumsAdapter.getAlbums().isEmpty()) {
                viewModel.makeApiCallGetAlbums(token)
            }
        }
    }

    private fun initMainObserver() {
        viewModel.albumsLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is AppState.Loading -> {
                    binding.loadingLayout.root.visibility = View.VISIBLE
                }
                is AppState.SuccessGetAlbums -> {
                    binding.loadingLayout.root.visibility = View.GONE
                    albumsAdapter.setAlbums(result.response)
                    viewModel.albumsLiveData.value = null
                }
                is AppState.Error -> {
                    binding.loadingLayout.root.visibility = View.GONE
                    Snackbar.make(
                        binding.root, String.format(
                            getString(R.string.app_state_error_text),
                            result.throwable.toString()
                        ), Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.snackbar_reload)) {
                        viewModel.makeApiCallGetAlbums(token)
                    }.show()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}