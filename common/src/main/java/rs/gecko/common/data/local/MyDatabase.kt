package rs.gecko.demoappnapredninivo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.gecko.demoappnapredninivo.data.local.dao.CommentDao
import rs.gecko.demoappnapredninivo.data.local.dao.PhotoDao
import rs.gecko.demoappnapredninivo.data.local.dao.PostDao
import rs.gecko.demoappnapredninivo.data.local.dao.UserPhotoDao
import rs.gecko.demoappnapredninivo.data.local.entities.CommentEntity
import rs.gecko.demoappnapredninivo.data.local.entities.PhotoEntity
import rs.gecko.demoappnapredninivo.data.local.entities.PostEntity
import rs.gecko.demoappnapredninivo.data.local.entities.UserPhotoEntity
import rs.gecko.demoappnapredninivo.data.local.typeconverters.ImageConverter

@Database(
    entities =[PostEntity::class, CommentEntity::class, PhotoEntity::class, UserPhotoEntity::class],
    version = 2
)
@TypeConverters(ImageConverter::class)
abstract class MyDatabase : RoomDatabase() {

    abstract fun getPostDao(): PostDao
    abstract fun getCommentDao(): CommentDao
    abstract fun getPhotoDao(): PhotoDao
    abstract fun getUserPhotoDao(): UserPhotoDao

    companion object {
        const val DATABASE_NAME: String = "my_db"
    }
}