package rs.gecko.demoappnapredninivo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_post.view.*
import kotlinx.android.synthetic.main.item_user_photo.view.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto

class UserPhotoRecyclerAdapter() : RecyclerView.Adapter<UserPhotoRecyclerAdapter.UserPhotoViewHolder>() {

    inner class UserPhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<UserPhoto>() {
        override fun areItemsTheSame(oldItem: UserPhoto, newItem: UserPhoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserPhoto, newItem: UserPhoto): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPhotoViewHolder {
        return UserPhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserPhotoViewHolder, position: Int) {
        val customPhoto = differ.currentList[position]
        holder.itemView.apply {
            customPhotoIv.setImageBitmap(customPhoto.photo)
            locationTv.text = "Location: To be implemented"

            setOnClickListener {
                onItemClickListener?.let {
                    it(customPhoto)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((UserPhoto) -> Unit)? = null

    fun setOnItemClickListener(listener: (UserPhoto) -> Unit) {
        onItemClickListener = listener
    }
}