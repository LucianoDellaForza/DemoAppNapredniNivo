 package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.dao.CommentDao
import rs.gecko.demoappnapredninivo.data.local.entities.CommentEntity
import rs.gecko.demoappnapredninivo.data.remote.JsonPlaceholderAPI
import rs.gecko.demoappnapredninivo.ui.models.Comment
import rs.gecko.demoappnapredninivo.util.Resource
import java.lang.Exception

class CommentRepositoryImpl
constructor(
    private val api: JsonPlaceholderAPI,
    private val commentDao: CommentDao
): CommentRepository {

    override suspend fun fetchAllComments(): Resource<List<Comment>> {
        return try {
            val response = api.getAllComments()
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it.map {
                        Comment(postId = it.postId, id = it.id, name = it.name, email = it.email, body = it.body)
                    })
                } ?: Resource.Error("An unknown error has occurred")
            } else {
                Resource.Error("An unknown error has occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Couldn't reach the server")
        }
    }

    override suspend fun insertAllComments(comments: List<CommentEntity>) {
        TODO("Not yet implemented")
    }


}