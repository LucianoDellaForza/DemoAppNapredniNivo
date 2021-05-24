package rs.gecko.demoappnapredninivo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rs.gecko.demoappnapredninivo.data.local.entities.PhotoEntity

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity): Long

    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<PhotoEntity>>
}