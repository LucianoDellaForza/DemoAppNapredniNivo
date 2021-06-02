package rs.gecko.demoappnapredninivo.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.gecko.demoappnapredninivo.repositories.PostRepository
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.util.Resource
import javax.inject.Inject

@HiltViewModel
class PostViewModel
//@ViewModelInject
@Inject
constructor(
    private val postRepository: PostRepository
    //@Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    val posts: LiveData<Resource<List<Post>>>
        get() = _posts

    init {
        fetchAllPosts()
    }

    fun fetchAllPosts() {
        _posts.postValue(Resource.Loading())
        viewModelScope.launch {
            val postsResult = postRepository.fetchAllPosts()
            _posts.postValue(postsResult)
        }
    }




}
