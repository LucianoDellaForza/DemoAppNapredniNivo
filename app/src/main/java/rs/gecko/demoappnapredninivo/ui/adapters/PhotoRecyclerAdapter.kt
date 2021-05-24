package rs.gecko.demoappnapredninivo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_photo.view.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.models.Photo

class PhotoRecyclerAdapter(
//    var albumsWithPhotos: HashMap<Int, List<Photo>>
    var albumPhotos: List<Photo>
) : RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_photo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        //val albumPhotos = albumsWithPhotos[position]
        val photo = albumPhotos[position]
        holder.itemView.apply {
//            Glide.with(context)
//                .load(photo.url)
//                .placeholder(R.drawable.placeholder_pic)
//                .into(photoIv)
            photoIdTv.text = photo.id.toString()
        }
    }

    override fun getItemCount(): Int {
        return albumPhotos.size
    }
}