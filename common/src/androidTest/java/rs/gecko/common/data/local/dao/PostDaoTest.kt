package rs.gecko.common.data.local.dao

import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import rs.gecko.common.getOrAwaitValue
import rs.gecko.demoappnapredninivo.data.local.dao.PostDao
import rs.gecko.demoappnapredninivo.data.local.entities.PostEntity

@ExperimentalCoroutinesApi
class PostDaoTest : DaoTest(){

    private lateinit var dao: PostDao

    @Before
    fun setupDao() {
        dao = database.getPostDao()
    }


    @Test
    fun insertPost() = runBlockingTest {
        val post = PostEntity(0, 1, "title", "body")
        dao.insertPost(post)

        //for dealing with live data - LiveDataUtilTest class (from Google)
        val allPosts = dao.getAllPosts().getOrAwaitValue()

        Truth.assertThat(allPosts).contains(post)
    }
}