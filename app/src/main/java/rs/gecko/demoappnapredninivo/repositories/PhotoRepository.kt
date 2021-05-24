package rs.gecko.demoappnapredninivo.repositories

import kotlinx.coroutines.flow.Flow
import rs.gecko.demoappnapredninivo.ui.models.Photo
import rs.gecko.demoappnapredninivo.util.Resource

interface PhotoRepository {

    suspend fun fetchAllPhotos(): Resource<HashMap<Int, MutableList<Photo>>>

    suspend fun insertAllPhotos(photoEntities: List<Photo>)
}