package rs.gecko.demoappnapredninivo.data.remote.responses

data class CommentResponse (
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
    )