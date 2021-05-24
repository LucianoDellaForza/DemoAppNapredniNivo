package rs.gecko.demoappnapredninivo.data.local.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userPhotos")
data class UserPhotoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "photo")
    val photo: Bitmap,

//    @ColumnInfo(name = "latitude")
//    val lat: Double? = 0.0,
//
//    @ColumnInfo(name = "longitude")
//    val lng: Double? = 0.0

    @ColumnInfo(name = "city")
    val city: String = "",

    @ColumnInfo(name = "area")
    val area: String = ""

    )