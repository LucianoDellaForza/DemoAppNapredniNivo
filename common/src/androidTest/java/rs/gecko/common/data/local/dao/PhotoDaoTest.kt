package rs.gecko.common.data.local.dao

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import rs.gecko.common.getOrAwaitValue
import rs.gecko.demoappnapredninivo.data.local.dao.PhotoDao
import rs.gecko.demoappnapredninivo.data.local.entities.PhotoEntity

@ExperimentalCoroutinesApi
class PhotoDaoTest : DaoTest(){

    private lateinit var dao: PhotoDao

    @Before
    fun setupDao() {
        dao = database.getPhotoDao()
    }


    @Test
    fun insertPhoto() = runBlockingTest {
        val photo = PhotoEntity(0, 1, "title", "url", "thumbnailUrl")
        dao.insertPhoto(photo)

        //for dealing with live data - LiveDataUtilTest class (from Google)
        val allPhotos = dao.getAllPhotos().getOrAwaitValue()

        Truth.assertThat(allPhotos).contains(photo)
    }
}