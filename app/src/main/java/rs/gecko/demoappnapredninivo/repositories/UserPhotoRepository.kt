package rs.gecko.demoappnapredninivo.repositories

import androidx.lifecycle.LiveData
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto


interface UserPhotoRepository {

    fun getAllUserPhotos(): LiveData<List<UserPhoto>>

    fun getUserPhoto(photoId: Int): LiveData<UserPhoto>

    suspend fun insertUserPhoto(userPhoto: UserPhoto): Int

}