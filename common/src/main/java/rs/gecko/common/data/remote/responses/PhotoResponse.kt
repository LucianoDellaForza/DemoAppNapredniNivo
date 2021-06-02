package rs.gecko.demoappnapredninivo.data.remote.responses


data class PhotoResponse (
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    var thumbnailUrl: String
    )