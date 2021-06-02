package rs.gecko.demoappnapredninivo.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.gecko.demoappnapredninivo.repositories.AlbumRepository
import rs.gecko.demoappnapredninivo.repositories.PhotoRepository
import rs.gecko.demoappnapredninivo.ui.models.Photo
import rs.gecko.demoappnapredninivo.util.Resource
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel
//@ViewModelInject
@Inject
constructor(
    private val photoRepository: PhotoRepository,
    private val albumRepository: AlbumRepository
): ViewModel() {

    private val _albumsWithPhotos: MutableLiveData<Resource<HashMap<Int, MutableList<Photo>>>> = MutableLiveData()

    val albumsWithPhotos: LiveData<Resource<HashMap<Int, MutableList<Photo>>>>
        get() = _albumsWithPhotos

    init {
        fetchAllPhotos()
    }

    fun fetchAllPhotos() {
        _albumsWithPhotos.postValue(Resource.Loading())
        viewModelScope.launch {
            val photosResult = photoRepository.fetchAllPhotos()
            val albumResult = albumRepository.fetchAllAlbums()
            _albumsWithPhotos.postValue(photosResult)
        }
    }

    
}