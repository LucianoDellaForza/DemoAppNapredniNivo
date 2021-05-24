package rs.gecko.demoappnapredninivo.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rs.gecko.demoappnapredninivo.repositories.UserPhotoRepository
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto

class UserPhotoViewModel
@ViewModelInject
constructor(
    private val userPhotoRepository: UserPhotoRepository
): ViewModel() {

//    private val _userPhotos: MutableLiveData<Resource<List<UserPhoto>>> = MutableLiveData()
//
//    val userPhotos: LiveData<Resource<List<UserPhoto>>>
//        get() = _userPhotos

    fun getAllUserPhotos() = userPhotoRepository.getAllUserPhotos()

    fun getUserPhoto(photoId: Int) = userPhotoRepository.getUserPhoto(photoId)

    suspend fun insertUserPhoto(userPhoto: UserPhoto) = viewModelScope.launch {
        userPhotoRepository.insertUserPhoto(userPhoto)
    }

}