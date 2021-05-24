package rs.gecko.common.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import rs.gecko.common.getOrAwaitValue
import rs.gecko.demoappnapredninivo.data.local.MyDatabase
import rs.gecko.demoappnapredninivo.data.local.dao.CommentDao
import rs.gecko.demoappnapredninivo.data.local.entities.CommentEntity

@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//@SmallTest
class CommentDaoTest : DaoTest() {

//    //rule for live data (it's asynchronous even tho we use runBlocking
//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var database: MyDatabase
    private lateinit var dao: CommentDao

    @Before
    fun setupDao() {
        dao = database.getCommentDao()
    }


    @Test
    fun insertComment() = runBlockingTest {
        val comment = CommentEntity(0, 1, "comment name", "email", "comment body")
        dao.insertComment(comment)

        //for dealing with live data - LiveDataUtilTest class (from Google)
        val allComments = dao.getAllComments().getOrAwaitValue()

        assertThat(allComments).contains(comment)
    }

}