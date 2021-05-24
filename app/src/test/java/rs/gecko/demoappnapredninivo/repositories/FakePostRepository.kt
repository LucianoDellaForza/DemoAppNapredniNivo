package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.entities.PostEntity
import rs.gecko.demoappnapredninivo.ui.models.Comment
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.util.Resource

class FakePostRepository : PostRepository {

    private var shouldReturnNetworkError = false

    fun setShouldNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun fetchAllPosts(): Resource<List<Post>> {
        return if(shouldReturnNetworkError) {
            Resource.Error("Error", null)
        } else {
            Resource.Success(listOf(Post(1, 1, "title", "body")))
        }
    }

    override suspend fun insertAllPosts(postEntities: List<PostEntity>) {
        TODO("Not yet implemented")
    }
}