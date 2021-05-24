package rs.gecko.demoappnapredninivo.ui.viewmodels

import org.junit.Before
import org.junit.Test
import rs.gecko.demoappnapredninivo.repositories.FakeUserPhotoRepository

class UserPhotoViewModelTest {

    private lateinit var viewModel: UserPhotoViewModel

    @Before
    fun setup() {
        viewModel = UserPhotoViewModel(FakeUserPhotoRepository())
    }

}