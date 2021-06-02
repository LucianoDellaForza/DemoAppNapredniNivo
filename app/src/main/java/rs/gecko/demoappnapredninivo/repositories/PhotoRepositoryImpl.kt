package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.dao.PhotoDao
import rs.gecko.demoappnapredninivo.data.remote.JsonPlaceholderAPI
import rs.gecko.demoappnapredninivo.ui.models.Photo
import rs.gecko.demoappnapredninivo.util.Resource
import java.lang.Exception

class PhotoRepositoryImpl
constructor (
    private val api: JsonPlaceholderAPI,
    private val photoDao: PhotoDao
    ) : PhotoRepository {


    override suspend fun fetchAllPhotos(): Resource<HashMap<Int, MutableList<Photo>>> {
        return  try {
            val photosResponse = api.getAllPhotos()
            if (photosResponse.isSuccessful) {
                photosResponse.body()?.let {
                    val photoList = it.map {
                        Photo(
                            albumId = it.albumId,
                            id = it.id,
                            title = it.title,
                            url = it.url,
                            thumbnailUrl = it.thumbnailUrl
                        )
                    }
                    //to album hashmap
                    val albumWithPhotos = HashMap<Int, MutableList<Photo>>()
                    for (photo in photoList) {
                        if (albumWithPhotos.containsKey(photo.albumId)) {
                            albumWithPhotos.get(photo.albumId)?.add(photo)
                        } else {
                            albumWithPhotos.put(photo.albumId, mutableListOf(photo))
                        }
                    }
                    return  Resource.Success(albumWithPhotos)
                } ?: Resource.Error("An unknown error has occurred")
            } else {
                Resource.Error("An unknown error has occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Couldn't reach the server")
        }
    }

    override suspend fun insertAllPhotos(photoEntities: List<Photo>) {
        TODO("Not yet implemented")
    }
}