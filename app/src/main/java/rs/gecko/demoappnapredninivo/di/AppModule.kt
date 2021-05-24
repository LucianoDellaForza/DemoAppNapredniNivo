package rs.gecko.demoappnapredninivo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import rs.gecko.demoappnapredninivo.data.local.dao.CommentDao
import rs.gecko.demoappnapredninivo.data.local.dao.PhotoDao
import rs.gecko.demoappnapredninivo.data.local.dao.PostDao
import rs.gecko.demoappnapredninivo.data.local.dao.UserPhotoDao
import rs.gecko.demoappnapredninivo.data.remote.JsonPlaceholderAPI
import rs.gecko.demoappnapredninivo.repositories.*
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

//    /** Retrofit dependencies **/
//    @Singleton
//    @Provides
//    fun provideGsonBuilder(): Gson {
//        return GsonBuilder()
//            .create()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(gson: Gson): Retrofit.Builder {
//        return Retrofit.Builder()
//            .baseUrl("https://jsonplaceholder.typicode.com/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//    }
//
//    @Singleton
//    @Provides
//    fun provideJsonPlaceHolderApi(retrofit: Retrofit.Builder): JsonPlaceholderAPI {
//        return retrofit
//            .build()
//            .create(JsonPlaceholderAPI::class.java)
//    }

//    /** Room dependencies **/
//    @Singleton
//    @Provides
//    fun provideMyDatabase(@ApplicationContext context: Context): MyDatabase {
//        return Room.databaseBuilder(
//            context,
//            MyDatabase::class.java,
//            MyDatabase.DATABASE_NAME
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun providePostDao(myDatabase: MyDatabase): PostDao {
//        return myDatabase.getPostDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideCommentDao(myDatabase: MyDatabase): CommentDao {
//        return myDatabase.getCommentDao()
//    }
//
//    @Singleton
//    @Provides
//    fun providePhotoDao(myDatabase: MyDatabase): PhotoDao {
//        return myDatabase.getPhotoDao()
//    }
//
//    @Singleton
//    @Provides
//    fun provideUserPhotoDao(myDatabase: MyDatabase): UserPhotoDao {
//        return myDatabase.getUserPhotoDao()
//    }

    /** Repository dependencies **/
    @Singleton
    @Provides
    fun providePostRepository(
        api: JsonPlaceholderAPI,
        postDao: PostDao
    ): PostRepository {
        return PostRepositoryImpl(api, postDao)
    }

    @Singleton
    @Provides
    fun provideCommentRepository(
        api: JsonPlaceholderAPI,
        commentDao: CommentDao
    ): CommentRepository {
        return CommentRepositoryImpl(api, commentDao)
    }

    @Singleton
    @Provides
    fun provideCustomPhotoRepository(
        customPhotoDao: UserPhotoDao
    ): UserPhotoRepository {
        return UserPhotoRepositoryImpl(customPhotoDao)
    }

    @Singleton
    @Provides
    fun provideUserPhotoRepository(
        api: JsonPlaceholderAPI,
        photoDao: PhotoDao
    ): PhotoRepository {
        return PhotoRepositoryImpl(api, photoDao)
    }

    @Singleton
    @Provides
    fun provideAlbumRepository(
        api: JsonPlaceholderAPI
    ): AlbumRepository {
        return AlbumRepositoryImpl(api)
    }
}