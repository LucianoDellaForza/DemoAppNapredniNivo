package rs.gecko.common.data.local.dao

import android.graphics.Bitmap
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import rs.gecko.common.getOrAwaitValue
import rs.gecko.demoappnapredninivo.data.local.dao.UserPhotoDao
import rs.gecko.demoappnapredninivo.data.local.entities.UserPhotoEntity

@ExperimentalCoroutinesApi
class UserPhotoDaoTest : DaoTest(){

    private lateinit var dao: UserPhotoDao

    @Before
    fun setupDao() {
        dao = database.getUserPhotoDao()
    }


    @Test
    fun insertUserPhoto() = runBlockingTest {
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val userPhoto = UserPhotoEntity(1, bitmap, 0.0, 0.0)
        dao.insertUserPhoto(userPhoto)

        //for dealing with live data - LiveDataUtilTest class (from Google)
        val allUserPhotos = dao.getAllUserPhotos().getOrAwaitValue()

        Truth.assertThat(allUserPhotos).contains(userPhoto)
    }
}