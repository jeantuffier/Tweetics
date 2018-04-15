package fr.jeantuffier.tweetics.common.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import fr.jeantuffier.tweetics.common.Config
import fr.jeantuffier.tweetics.common.room.ApplicationDatabase
import fr.jeantuffier.tweetics.politician.repository.PoliticianService
import fr.jeantuffier.tweetics.tweets.repository.TweetService
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
    fun providesHomeService(retrofit: Retrofit): PoliticianService =
        retrofit.create(PoliticianService::class.java)

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
    fun providesPoliticianDao(database: ApplicationDatabase) = database.ministerDao()

    @Singleton
    @Provides
    fun providesTweetDao(database: ApplicationDatabase) = database.tweetDao()

}
