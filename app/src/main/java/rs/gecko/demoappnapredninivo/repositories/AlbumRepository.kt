package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.ui.models.Album
import rs.gecko.demoappnapredninivo.util.Resource

interface AlbumRepository {

    suspend fun fetchAllAlbums(): Resource<List<Album>>
}