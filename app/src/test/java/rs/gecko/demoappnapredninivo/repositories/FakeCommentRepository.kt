package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.entities.CommentEntity
import rs.gecko.demoappnapredninivo.ui.models.Comment
import rs.gecko.demoappnapredninivo.util.Resource

class FakeCommentRepository : CommentRepository {

    private var shouldReturnNetworkError = false

    fun setShouldNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override suspend fun fetchAllComments(): Resource<List<Comment>> {
        return if(shouldReturnNetworkError) {
             Resource.Error("Error", null)
        } else {
            Resource.Success(listOf(Comment(0, 1, "name", "email", "body")))
        }
    }

    override suspend fun insertAllComments(comments: List<CommentEntity>) {
        TODO("Not yet implemented")
    }
}