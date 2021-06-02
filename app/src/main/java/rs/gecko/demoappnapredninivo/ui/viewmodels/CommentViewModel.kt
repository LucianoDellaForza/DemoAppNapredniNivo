package rs.gecko.demoappnapredninivo.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import rs.gecko.demoappnapredninivo.repositories.CommentRepository
import rs.gecko.demoappnapredninivo.ui.models.Comment
import rs.gecko.demoappnapredninivo.util.Resource
import javax.inject.Inject

@HiltViewModel
class CommentViewModel
//@ViewModelInject
@Inject
constructor(
    private val commentRepository: CommentRepository
): ViewModel() {

    private val _comments: MutableLiveData<Resource<List<Comment>>> = MutableLiveData()

    val comments: LiveData<Resource<List<Comment>>>
        get() = _comments

    init {
        fetchAllComments()
    }

    fun fetchAllComments() {
        _comments.postValue(Resource.Loading())
        viewModelScope.launch {
            val commentsResult = commentRepository.fetchAllComments()
            _comments.postValue(commentsResult)
        }
    }
}