package rs.gecko.demoappnapredninivo.ui.viewmodels

import org.junit.Before
import org.junit.Test
import rs.gecko.demoappnapredninivo.repositories.FakePostRepository

class PostViewModelTest {

    private lateinit var viewModel: PostViewModel

    @Before
    fun setup() {
        viewModel = PostViewModel(FakePostRepository())
    }


}