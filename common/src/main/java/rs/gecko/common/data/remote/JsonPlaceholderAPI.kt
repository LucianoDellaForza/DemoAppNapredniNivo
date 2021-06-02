package rs.gecko.demoappnapredninivo.data.remote

import retrofit2.Response
import retrofit2.http.GET
import rs.gecko.demoappnapredninivo.data.remote.responses.AlbumResponse
import rs.gecko.demoappnapredninivo.data.remote.responses.CommentResponse
import rs.gecko.demoappnapredninivo.data.remote.responses.PhotoResponse
import rs.gecko.demoappnapredninivo.data.remote.responses.PostResponse

interface JsonPlaceholderAPI {

    @GET("posts")
    suspend fun getAllPosts(): Response<List<PostResponse>>

    @GET("comments")
    suspend fun getAllComments(): Response<List<CommentResponse>>

    @GET("photos")
    suspend fun getAllPhotos(): Response<List<PhotoResponse>>

    @GET("albums")
    suspend fun getAllAlbums(): Response<List<AlbumResponse>>
}