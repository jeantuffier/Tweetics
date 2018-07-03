package fr.jeantuffier.tweetics.presentation.tweet

import fr.jeantuffier.tweetics.data.datastore.tweets.LocalTweetsDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.tweets.RemoteTweetsDataStoreImpl
import fr.jeantuffier.tweetics.data.factory.TweetsFactory
import fr.jeantuffier.tweetics.data.repository.TweetsRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.TweetsService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.domain.datastore.LocalTweetsDataStore
import fr.jeantuffier.tweetics.domain.datastore.RemoteTweetsDataStore
import fr.jeantuffier.tweetics.domain.repositories.TweetsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

object TweetModule {

    val module = applicationContext {

        bean { get<ApplicationDatabase>().tweetDao() }

        bean { TweetsFactory() }

        bean {
            LocalTweetsDataStoreImpl(
                get(), //TweetDao
                get() //TweetsFactory
            ) as LocalTweetsDataStore
        }

        bean { get<Retrofit>().create(TweetsService::class.java) }

        bean {
            RemoteTweetsDataStoreImpl(
                get(), //TweetsService
                get() //TweetsFactory
            ) as RemoteTweetsDataStore
        }

        bean {
            TweetsRepositoryImpl(
                this.androidApplication(),
                get(), //LocalTweetsDataStore
                get() //RemoteTweetsDataStore
            ) as TweetsRepository
        }

        bean {
            TweetPresenter(
                get() //TweetsRepository
            ) as TweetContract.Presenter
        }

        bean { TweetParser(this.androidApplication()) }

        bean { TweetOnItemClickHandler(this.androidApplication()) }

        bean {
            TweetAdapter(
                get(), //TweetParser
                get() // TweetsOnItemClickHandler
            )
        }

    }

}
