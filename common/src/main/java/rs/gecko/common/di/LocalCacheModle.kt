package rs.gecko.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.gecko.demoappnapredninivo.data.local.MyDatabase
import rs.gecko.demoappnapredninivo.data.local.dao.CommentDao
import rs.gecko.demoappnapredninivo.data.local.dao.PhotoDao
import rs.gecko.demoappnapredninivo.data.local.dao.PostDao
import rs.gecko.demoappnapredninivo.data.local.dao.UserPhotoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)   //ApplicationComponent
object LocalCacheModle {

    /** Room dependencies **/
    @Singleton
    @Provides
    fun provideMyDatabase(@ApplicationContext context: Context): MyDatabase {
        return Room.databaseBuilder(
            context,
            MyDatabase::class.java,
            MyDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providePostDao(myDatabase: MyDatabase): PostDao {
        return myDatabase.getPostDao()
    }

    @Singleton
    @Provides
    fun provideCommentDao(myDatabase: MyDatabase): CommentDao {
        return myDatabase.getCommentDao()
    }

    @Singleton
    @Provides
    fun providePhotoDao(myDatabase: MyDatabase): PhotoDao {
        return myDatabase.getPhotoDao()
    }

    @Singleton
    @Provides
    fun provideUserPhotoDao(myDatabase: MyDatabase): UserPhotoDao {
        return myDatabase.getUserPhotoDao()
    }
}