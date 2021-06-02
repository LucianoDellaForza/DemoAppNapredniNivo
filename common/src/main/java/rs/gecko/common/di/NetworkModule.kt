package rs.gecko.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.gecko.demoappnapredninivo.data.remote.JsonPlaceholderAPI
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)   //ApplicationComponent
object NetworkModule {

    /** Retrofit dependencies **/
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideJsonPlaceHolderApi(retrofit: Retrofit.Builder): JsonPlaceholderAPI {
        return retrofit
            .build()
            .create(JsonPlaceholderAPI::class.java)
    }
}