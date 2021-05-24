package rs.gecko.common.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import rs.gecko.demoappnapredninivo.data.local.MyDatabase

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
open class DaoTest{

    //rule for live data (it's asynchronous even tho we use runBlocking
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected lateinit var database: MyDatabase

    @Before
    open fun setupDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MyDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun teardown() {
        database.close()
    }
}