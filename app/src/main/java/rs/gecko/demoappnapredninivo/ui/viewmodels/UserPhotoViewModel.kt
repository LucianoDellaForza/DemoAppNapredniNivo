package rs.gecko.demoappnapredninivo.ui.viewmodels


import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.gecko.demoappnapredninivo.repositories.UserPhotoRepository
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto
import javax.inject.Inject

@HiltViewModel
class UserPhotoViewModel
//@ViewModelInject
@Inject
constructor(
    private val userPhotoRepository: UserPhotoRepository
): ViewModel() {

    private val _userPhotos: MutableLiveData<List<UserPhoto>> = MutableLiveData()

    val userPhotos: LiveData<List<UserPhoto>>
        get() = _userPhotos

    init {
        getAllUserPhotos()
    }

    fun getAllUserPhotos() {
        val result = userPhotoRepository.getAllUserPhotos()
        _userPhotos.postValue(result.value)
    }

    fun getUserPhoto(photoId: Int) = userPhotoRepository.getUserPhoto(photoId)

    suspend fun insertUserPhoto(userPhoto: UserPhoto) = viewModelScope.launch {
        userPhotoRepository.insertUserPhoto(userPhoto)
    }

}