package fr.jeantuffier.tweetics.presentation.common

import android.app.Application
import android.arch.persistence.room.Room
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.presentation.home.HomeModule
import fr.jeantuffier.tweetics.presentation.politician.PoliticianModule
import fr.jeantuffier.tweetics.presentation.tweets.TweetModule
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val DATABASE_NAME = "database"

class Tweetics : Application() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Config.TWEETICS_SERVER)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val database by lazy {
        Room
            .databaseBuilder(this, ApplicationDatabase::class.java, DATABASE_NAME)
            .build()
    }

    val appModule: Module = applicationContext {
        bean { retrofit }
        bean { database }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(
            this, listOf(
                appModule,
                HomeModule.module,
                PoliticianModule.module,
                TweetModule.module
            )
        )
    }
}