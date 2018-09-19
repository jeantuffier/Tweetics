package fr.jeantuffier.tweetics.presentation.wall

import fr.jeantuffier.tweetics.data.datastore.wall.LocalWallDataStore
import fr.jeantuffier.tweetics.data.datastore.wall.LocalWallDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.wall.RemoteWallDataStore
import fr.jeantuffier.tweetics.data.datastore.wall.RemoteWallDataStoreImpl
import fr.jeantuffier.tweetics.data.repository.WallRepository
import fr.jeantuffier.tweetics.data.repository.WallRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.WallService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.domain.usecase.link.GetLocalLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.link.InsertLinkUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.GetLocalPoliticianUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.InsertPoliticianUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.GetLocalTweetUseCase
import fr.jeantuffier.tweetics.domain.usecase.tweet.InsertTweetUseCase
import fr.jeantuffier.tweetics.presentation.tweet.TweetParser
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

object WallModule {

    val module = applicationContext {

        bean { get<ApplicationDatabase>().linkDao() }

        bean { get<ApplicationDatabase>().tweetDao() }

        bean { get<ApplicationDatabase>().politicianDao() }

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
                get(), //TweetDao
                get() //GetLocalPoliticianUseCase
            )
        }

        bean {
            InsertLinkUseCase(
                get() //LinkDao
            )
        }

        bean {
            InsertPoliticianUseCase(
                get() //TweetDao
            )
        }

        bean {
            InsertTweetUseCase(
                get() //TweetDao
            )
        }

        bean {
            LocalWallDataStoreImpl(
                get(), //GetLinkUseCase
                get(), //GetLocalPoliticianUseCase
                get(), //GetLocalTweetUseCase
                get(), //InsertLinkUseCase
                get(), //InsertPoliticianUseCase
                get()  //InsertTweetUseCase
            ) as LocalWallDataStore
        }

        bean { get<Retrofit>().create(WallService::class.java) }

        bean {
            RemoteWallDataStoreImpl(
                get() //WallService
            ) as RemoteWallDataStore
        }

        bean {
            WallRepositoryImpl(
                this.androidApplication(),
                get(), //LocalWallDataStore
                get() //RemoteWallDataStore
            ) as WallRepository
        }

        bean {
            WallPresenter(
                get() //WallRepository
            ) as WallContract.Presenter
        }

        bean { TweetParser(this.androidApplication()) }

        bean { WallOnItemClickHandler(this.androidApplication()) }

        bean {
            WallAdapter(
                get(), //TweetParser
                get() // WallOnItemClickHandler
            )
        }

    }

}
