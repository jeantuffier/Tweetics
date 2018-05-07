package fr.jeantuffier.tweetics.presentation.politician.di

import android.content.Context
import dagger.Module
import dagger.Provides
import fr.jeantuffier.tweetics.data.datastore.politicians.LocalPoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.politicians.RemotePoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.mapper.PoliticiansMapper
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
        politicianDao: PoliticianDao,
        politiciansMapper: PoliticiansMapper
    ): LocalPoliticiansDataStore {
        return LocalPoliticiansDataStoreImpl(
            politicianDao,
            politiciansMapper
        )
    }

    @PoliticianActivityScope
    @Provides
    fun providesRemotePoliticiansDataStore(
        politicianService: PoliticianService,
        politiciansMapper: PoliticiansMapper
    ): RemotePoliticiansDataStore {
        return RemotePoliticiansDataStoreImpl(
            politicianService,
            politiciansMapper
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