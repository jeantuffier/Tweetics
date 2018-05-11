package fr.jeantuffier.tweetics.presentation.tweets.di

import android.content.Context
import dagger.Module
import dagger.Provides
import fr.jeantuffier.tweetics.data.datastore.tweets.LocalTweetsDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.tweets.RemoteTweetsDataStoreImpl
import fr.jeantuffier.tweetics.data.mapper.TweetsFactory
import fr.jeantuffier.tweetics.data.repository.TweetsRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.TweetsService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.data.room.dao.TweetDao
import fr.jeantuffier.tweetics.domain.datastore.LocalTweetsDataStore
import fr.jeantuffier.tweetics.domain.datastore.RemoteTweetsDataStore
import fr.jeantuffier.tweetics.domain.repositories.TweetsRepository
import fr.jeantuffier.tweetics.presentation.tweets.TweetsContract
import fr.jeantuffier.tweetics.presentation.tweets.TweetsPresenter
import retrofit2.Retrofit

@Module
class TweetsContractPresenterModule {

    @TweetsActivityScope
    @Provides
    fun providesTweetService(retrofit: Retrofit): TweetsService =
        retrofit.create(TweetsService::class.java)

    @TweetsActivityScope
    @Provides
    fun providesTweetDao(database: ApplicationDatabase) = database.tweetDao()

    @TweetsActivityScope
    @Provides
    fun providesLocalPoliticiansDataStore(
        tweetDao: TweetDao,
        tweetsMapper: TweetsFactory
    ): LocalTweetsDataStore {
        return LocalTweetsDataStoreImpl(
            tweetDao,
            tweetsMapper
        )
    }

    @TweetsActivityScope
    @Provides
    fun providesRemoteTweetsDataStore(
        tweetsService: TweetsService,
        tweetsMapper: TweetsFactory
    ): RemoteTweetsDataStore {
        return RemoteTweetsDataStoreImpl(
            tweetsService,
            tweetsMapper
        )
    }

    @TweetsActivityScope
    @Provides
    fun providesPoliticiansRepository(
        context: Context,
        localTweetsDataStore: LocalTweetsDataStore,
        remoteTweetsDataStore: RemoteTweetsDataStore
    ): TweetsRepository {
        return TweetsRepositoryImpl(
            context,
            localTweetsDataStore,
            remoteTweetsDataStore
        )
    }

    @TweetsActivityScope
    @Provides
    fun providesPoliticianContractPresenter(
        tweetsRepository: TweetsRepository,
        view: TweetsContract.View
    ): TweetsContract.Presenter {
        return TweetsPresenter(tweetsRepository, view)
    }

}