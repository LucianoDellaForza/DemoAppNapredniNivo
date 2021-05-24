package rs.gecko.demoappnapredninivo.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rs.gecko.demoappnapredninivo.data.local.entities.PostEntity

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity): Long

    @Query("SELECT * FROM posts")
    fun getAllPosts(): LiveData<List<PostEntity>>
}