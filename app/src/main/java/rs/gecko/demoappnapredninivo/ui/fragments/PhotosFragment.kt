package rs.gecko.demoappnapredninivo.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_posts.isLoadingPostsPb
import kotlinx.android.synthetic.main.item_album.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.adapters.AlbumRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.adapters.PhotoRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.adapters.PostRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.models.Photo
import rs.gecko.demoappnapredninivo.ui.viewmodels.PhotoViewModel
import rs.gecko.demoappnapredninivo.util.Resource

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos) {

    private val viewModel: PhotoViewModel by viewModels()
    lateinit var albumAdapter: AlbumRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setupRecyclers()

        viewModel.fetchAllPhotos()
    }

    private fun setupRecyclers() {
        albumAdapter = AlbumRecyclerAdapter(hashMapOf())
        rvAlbums.apply {
            adapter = albumAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun subscribeToObservers() {
        viewModel.albumsWithPhotos.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        albumAdapter.albumsWithPhotos = it
                        albumAdapter.notifyDataSetChanged()
                        Log.d("PhotosFragment", "Received photos")
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let { message ->
                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        isLoadingPhotosPb.visibility = View.GONE
    }

    private fun showProgressBar() {
        isLoadingPhotosPb.visibility = View.VISIBLE
    }
}