package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.entities.CommentEntity
import rs.gecko.demoappnapredninivo.ui.models.Comment
import rs.gecko.demoappnapredninivo.util.Resource

interface CommentRepository {

    suspend fun fetchAllComments(): Resource<List<Comment>>

    suspend fun insertAllComments(comments: List<CommentEntity>)
}