package rs.gecko.demoappnapredninivo.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import rs.gecko.demoappnapredninivo.data.local.dao.UserPhotoDao
import rs.gecko.demoappnapredninivo.data.local.entities.UserPhotoEntity
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto

class UserPhotoRepositoryImpl
constructor(
    private val userPhotoDao: UserPhotoDao
): UserPhotoRepository {

    override fun getAllUserPhotos(): LiveData<List<UserPhoto>> {
        return userPhotoDao.getAllUserPhotos().map {
            it.map {
                UserPhoto(id = it.id, photo = it.photo)
            }
        }
    }

    override fun getUserPhoto(photoId: Int): LiveData<UserPhoto> {
        return userPhotoDao.getUserPhoto(photoId).map {
            UserPhoto(id = it.id, photo = it.photo)
        }
    }

    override suspend fun insertUserPhoto(userPhoto: UserPhoto): Int {
        return userPhotoDao.insertUserPhoto(
            UserPhotoEntity(id = userPhoto.id, photo = userPhoto.photo, lat = userPhoto.lat, lng = userPhoto.lng)
        ).toInt()
    }
}