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
import kotlinx.android.synthetic.main.fragment_posts.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.adapters.PostRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.viewmodels.PostViewModel
import rs.gecko.demoappnapredninivo.util.Resource

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewModel: PostViewModel by viewModels()
    lateinit var postAdapter: PostRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setupRecycler()

        viewModel.fetchAllPosts()
    }

    private fun setupRecycler() {
        postAdapter = PostRecyclerAdapter()
        rvPosts.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun subscribeToObservers() {
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        postAdapter.differ.submitList(it)
                        Log.d("PostsFragment", "Received comments")
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
        isLoadingPostsPb.visibility = View.GONE
    }

    private fun showProgressBar() {
        isLoadingPostsPb.visibility = View.VISIBLE
    }
}