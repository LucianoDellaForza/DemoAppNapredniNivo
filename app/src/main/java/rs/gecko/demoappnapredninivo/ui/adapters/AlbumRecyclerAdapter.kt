package rs.gecko.demoappnapredninivo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_album.view.*
import kotlinx.android.synthetic.main.item_album.view.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.models.Photo

class AlbumRecyclerAdapter(
    var albumsWithPhotos: HashMap<Int, MutableList<Photo>>
//    var albumIds: List<Int>
) : RecyclerView.Adapter<AlbumRecyclerAdapter.AlbumViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    inner class AlbumViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val rvPhotos =  itemView.findViewById(R.id.rvPhotos) as RecyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_album,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {

        //        val albumPhotos = albumsWithPhotos[position]
        val albumIds = albumsWithPhotos.keys.toList()
        val albumId = albumIds[position]    //position msm da nije precizno
        holder.itemView.apply {
            albumIdTitle.text = "Album id: " + albumId.toString()
        }


//        val photos = mutableListOf<List<Photo>>()
//        for (photoKey in albumsWithPhotos.keys) {
//            albumsWithPhotos.get(photoKey)?.let {
//                photos.add(it)
//            }
//        }



        //child adapter stuff
        val photos = albumsWithPhotos.get(albumId)  //position
        // Create layout manager with initial prefetch item count
        val layoutManager = GridLayoutManager(
            holder.rvPhotos.context,
            3
        )
        if (photos != null) {
            layoutManager.initialPrefetchItemCount = photos.size
        }
        // Create sub item view adapter
        val photoAdapter = photos?.let { PhotoRecyclerAdapter(it) }

        holder.rvPhotos.layoutManager = layoutManager
        holder.rvPhotos.adapter = photoAdapter
        holder.rvPhotos.setRecycledViewPool(viewPool)


    }

    override fun getItemCount(): Int {
        return albumsWithPhotos.keys.size
    }
}