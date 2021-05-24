package rs.gecko.demoappnapredninivo.repositories

import rs.gecko.demoappnapredninivo.data.local.dao.PostDao
import rs.gecko.demoappnapredninivo.data.local.entities.PostEntity
import rs.gecko.demoappnapredninivo.data.remote.JsonPlaceholderAPI
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.util.Resource
import java.lang.Exception

class PostRepositoryImpl
constructor(
    private val api: JsonPlaceholderAPI,
    private val postDao: PostDao
): PostRepository {

    override suspend fun fetchAllPosts(): Resource<List<Post>> {
        return try {
            val response = api.getAllPosts()
            if (response.isSuccessful) {
                response.body()?.let {
                    return Resource.Success(it.map {
                        Post(userId = it.userId, id = it.id, title = it.title, body = it.body)
                    })
                } ?: Resource.Error("An unknown error has occurred")
            } else {
                Resource.Error("An unknown error has occurred")
            }
        } catch (e: Exception) {
            return Resource.Error("Couldn't reach the server")
        }
    }

    override suspend fun insertAllPosts(postEntities: List<PostEntity>) {
        TODO("Not yet implemented")
    }
}