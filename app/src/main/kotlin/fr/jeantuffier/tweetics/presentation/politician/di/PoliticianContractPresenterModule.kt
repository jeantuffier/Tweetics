package fr.jeantuffier.tweetics.presentation.politician.di

import android.content.Context
import dagger.Module
import dagger.Provides
import fr.jeantuffier.tweetics.data.datastore.politicians.LocalPoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.politicians.RemotePoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.mapper.LocalPoliticianMapper
import fr.jeantuffier.tweetics.data.mapper.RemotePoliticianMapper
import fr.jeantuffier.tweetics.data.repository.PoliticiansRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.PoliticianService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.data.room.dao.PoliticianDao
import fr.jeantuffier.tweetics.domain.datastore.LocalPoliticiansDataStore
import fr.jeantuffier.tweetics.domain.datastore.RemotePoliticiansDataStore
import fr.jeantuffier.tweetics.domain.repositories.PoliticiansRepository
import fr.jeantuffier.tweetics.presentation.politician.PoliticianContract
import fr.jeantuffier.tweetics.presentation.politician.PoliticianPresenter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PoliticianContractPresenterModule {

    @PoliticianActivityScope
    @Provides
    fun providesHomeService(retrofit: Retrofit): PoliticianService =
        retrofit.create(PoliticianService::class.java)

    @PoliticianActivityScope
    @Provides
    fun providesPoliticianDao(database: ApplicationDatabase) = database.ministerDao()

    @PoliticianActivityScope
    @Provides
    fun providesLocalPoliticiansDataStore(
        politicianDao: PoliticianDao
    ): LocalPoliticiansDataStore {
        return LocalPoliticiansDataStoreImpl(
            politicianDao,
            LocalPoliticianMapper()
        )
    }

    @PoliticianActivityScope
    @Provides
    fun providesRemotePoliticiansDataStore(
        politicianService: PoliticianService
    ): RemotePoliticiansDataStore {
        return RemotePoliticiansDataStoreImpl(
            politicianService,
            RemotePoliticianMapper()
        )
    }

    @PoliticianActivityScope
    @Provides
    fun providesPoliticiansRepository(
        context: Context,
        localPoliticiansDataStore: LocalPoliticiansDataStore,
        remotePoliticiansDataStore: RemotePoliticiansDataStore
    ): PoliticiansRepository {
        return PoliticiansRepositoryImpl(
            context,
            localPoliticiansDataStore,
            remotePoliticiansDataStore
        )
    }

    @PoliticianActivityScope
    @Provides
    fun providesPoliticianContractPresenter(
        politiciansRepository: PoliticiansRepository,
        view: PoliticianContract.View
    ): PoliticianContract.Presenter {
        return PoliticianPresenter(politiciansRepository, view)
    }

}