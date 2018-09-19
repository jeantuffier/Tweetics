package fr.jeantuffier.tweetics.presentation.tweet

import fr.jeantuffier.tweetics.data.datastore.tweet.LocalTweetDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.tweet.RemoteTweetDataStoreImpl
import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.data.repository.TweetsRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.TweetsService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.data.datastore.tweet.LocalTweetsDataStore
import fr.jeantuffier.tweetics.data.datastore.tweet.RemoteTweetsDataStore
import fr.jeantuffier.tweetics.data.repository.TweetsRepository
import fr.jeantuffier.tweetics.domain.usecase.link.GetLocalLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.link.InsertLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.GetLocalPoliticianUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.InsertPoliticianUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.GetLocalTweetUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.InsertTweetUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

object TweetModule {

    val module = applicationContext {

        bean { get<ApplicationDatabase>().tweetDao() }

        bean {
            GetLocalLinkUseCase(
                get() //LinkDao
            )
        }

        bean {
            GetLocalPoliticianUseCase(
                get() //PoliticianDao
            )
        }

        bean {
            GetLocalTweetUseCase(
                get(), //GetLocalLinkUseCase
                get(), //GetLocalPoliticianUseCase
                get()  //TweetDao
            )
        }

        bean {
            InsertLinkUseCase(
                get() //LinkDao
            )
        }

        bean {
            InsertPoliticianUseCase(
                get() //PoliticianDao
            )
        }

        bean {
            InsertTweetUseCase(
                get(), //InsertLinkUseCase
                get(), //InsertPoliticianUseCase
                get()  //TweetDao
            )
        }

        bean {
            LocalTweetDataStoreImpl(
                get(), //GetLocalTweetUseCase
                get()  //InsertLocalTweetUseCase
            ) as LocalTweetsDataStore
        }

        bean { get<Retrofit>().create(TweetsService::class.java) }

        bean {
            RemoteTweetDataStoreImpl(
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
