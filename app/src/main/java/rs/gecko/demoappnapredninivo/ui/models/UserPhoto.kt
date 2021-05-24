package rs.gecko.demoappnapredninivo.ui.models

import android.graphics.Bitmap

data class UserPhoto (
    var id: Int,
    val photo: Bitmap,
//    val lat: Double? = 0.0,
//    val lng: Double? = 0.0
    val city: String = "",
    val area: String = ""
    )