package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.entities.PostEntity
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.util.Resource

interface PostRepository {

    suspend fun fetchAllPosts(): Resource<List<Post>>

    suspend fun insertAllPosts(postEntities: List<PostEntity>)
}