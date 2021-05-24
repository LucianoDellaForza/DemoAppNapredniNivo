package rs.gecko.demoappnapredninivo.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto

class FakeUserPhotoRepository : UserPhotoRepository {

    private val userPhotos = mutableListOf<UserPhoto>()

    private val observableUserPhotos = MutableLiveData<List<UserPhoto>>(userPhotos)
    private val observableUserPhoto = MutableLiveData<UserPhoto>()

    override fun getAllUserPhotos(): LiveData<List<UserPhoto>> {
        return observableUserPhotos
    }

    override fun getUserPhoto(photoId: Int): LiveData<UserPhoto> {
        return observableUserPhoto
    }

    override suspend fun insertUserPhoto(userPhoto: UserPhoto): Int {
        userPhotos.add(userPhoto)
        refreshObservableUserPhotos()
        refreshObservableUserPhoto(userPhoto.id)
        return userPhoto.id
    }

    private fun refreshObservableUserPhotos() {
        observableUserPhotos.postValue(userPhotos)
    }

    private fun refreshObservableUserPhoto(userPhotoId: Int) {
        observableUserPhoto.postValue(userPhotos.filter { it.id == userPhotoId}[0])
    }


}