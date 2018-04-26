package fr.jeantuffier.tweetics.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import fr.jeantuffier.tweetics.presentation.common.Config
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.data.retrofit.service.TweetService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesContext() = context

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.TWEETICS_SERVER)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesTweetService(retrofit: Retrofit): TweetService =
        retrofit.create(TweetService::class.java)

    @Singleton
    @Provides
    fun providesDatabase(context: Context): ApplicationDatabase {
        return Room
            .databaseBuilder(context, ApplicationDatabase::class.java, "database")
            .build()
    }

    @Singleton
    @Provides
    fun providesTweetDao(database: ApplicationDatabase) = database.tweetDao()

}
