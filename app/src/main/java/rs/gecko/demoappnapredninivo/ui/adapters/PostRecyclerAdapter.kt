package rs.gecko.demoappnapredninivo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.models.Post

class PostRecyclerAdapter() : RecyclerView.Adapter<PostRecyclerAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = differ.currentList[position]
        holder.itemView.apply {
            titleTv.text = post.title
            postContentTv.text = post.body
        }
    }


}