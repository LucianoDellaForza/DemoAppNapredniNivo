package rs.gecko.demoappnapredninivo.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import rs.gecko.demoappnapredninivo.repositories.PostRepository
import rs.gecko.demoappnapredninivo.ui.models.Post
import rs.gecko.demoappnapredninivo.util.Resource

class PostViewModel
@ViewModelInject
constructor(
    private val postRepository: PostRepository
    //@Assisted private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _posts: MutableLiveData<Resource<List<Post>>> = MutableLiveData()

    val posts: LiveData<Resource<List<Post>>>
        get() = _posts

    fun fetchAllPosts() {
        _posts.postValue(Resource.Loading())
        viewModelScope.launch {
            val postsResult = postRepository.fetchAllPosts()
            _posts.postValue(postsResult)
        }
    }
}
