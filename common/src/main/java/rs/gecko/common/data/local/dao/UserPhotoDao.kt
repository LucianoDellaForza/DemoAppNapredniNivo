package rs.gecko.demoappnapredninivo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rs.gecko.demoappnapredninivo.data.local.entities.UserPhotoEntity

@Dao
interface UserPhotoDao {

    @Query("SELECT * FROM userPhotos")
    fun getAllUserPhotos(): LiveData<List<UserPhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPhoto(userPhoto: UserPhotoEntity): Long

    @Query("SELECT * FROM userPhotos WHERE id = :userPhotoId")
    fun getUserPhoto(userPhotoId: Int): LiveData<UserPhotoEntity>
}