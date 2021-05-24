package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.ui.models.Photo
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.util.Resource

class FakePhotoRepository : PhotoRepository {

    private var shouldReturnNetworkError = false

    fun setShouldNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun fetchAllPhotos(): Resource<HashMap<Int, MutableList<Photo>>> {
        return if(shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(
                hashMapOf(Pair(1, mutableListOf(Photo(1, 1, "title", "url", "thumbnailUrl"))))
            )
        }
    }

    override suspend fun insertAllPhotos(photoEntities: List<Photo>) {
        TODO("Not yet implemented")
    }
}