package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.remote.JsonPlaceholderAPI
import rs.gecko.demoappnapredninivo.ui.models.Album
import rs.gecko.demoappnapredninivo.util.Resource
import java.lang.Exception

class AlbumRepositoryImpl(
    val api: JsonPlaceholderAPI
): AlbumRepository {

    override suspend fun fetchAllAlbums(): Resource<List<Album>> {
        return try {
            val response = api.getAllAlbums()
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it.map {
                        Album(userId = it.userId, id = it.id, title = it.title)
                    })
                } ?: Resource.Error("An unknown error has occurred")
            } else {
                Resource.Error("An unknown error has occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Couldn't reach the server")
        }
    }
}