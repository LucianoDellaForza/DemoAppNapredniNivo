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
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_posts.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.adapters.CommentRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.viewmodels.CommentViewModel
import rs.gecko.demoappnapredninivo.util.Resource

@AndroidEntryPoint
class CommentsFragment : Fragment(R.layout.fragment_comments) {

    private val viewModel: CommentViewModel by viewModels()
    lateinit var commentAdapter: CommentRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
        setupRecycler()

        viewModel.fetchAllComments()
    }

    private fun setupRecycler() {
        commentAdapter = CommentRecyclerAdapter()
        rvComments.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun subscribeToObservers() {
        viewModel.comments.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        commentAdapter.differ.submitList(it)
                        Log.d("CommentsFragment", "Received comments")
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
        isLoadingCommentsPb.visibility = View.GONE
    }

    private fun showProgressBar() {
        isLoadingCommentsPb.visibility = View.VISIBLE
    }
}