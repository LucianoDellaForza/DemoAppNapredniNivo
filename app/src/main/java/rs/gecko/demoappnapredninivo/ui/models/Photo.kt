package rs.gecko.demoappnapredninivo.ui.models

data class Photo (
    val albumId: Int,
    val id: Int,
    val title: String,
    val url: String,
    var thumbnailUrl: String
)