package fr.jeantuffier.tweetics.presentation.politician

import fr.jeantuffier.tweetics.data.datastore.politicians.LocalPoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.politicians.RemotePoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.factory.PoliticiansFactory
import fr.jeantuffier.tweetics.data.repository.PoliticiansRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.PoliticianService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.domain.datastore.LocalPoliticiansDataStore
import fr.jeantuffier.tweetics.domain.datastore.RemotePoliticiansDataStore
import fr.jeantuffier.tweetics.domain.repositories.PoliticiansRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

object PoliticianModule {

    val module: Module = applicationContext {
        bean { PoliticianOnItemClickHandler(this.androidApplication()) }

        bean { PoliticianAdapter(get()) }

        bean { get<ApplicationDatabase>().politicianDao() }

        bean { PoliticiansFactory() }

        bean {
            LocalPoliticiansDataStoreImpl(
                get(), //PoliticianDao
                get() //PoliticiansFactory
            ) as LocalPoliticiansDataStore
        }

        bean { get<Retrofit>().create(PoliticianService::class.java) }

        bean {
            RemotePoliticiansDataStoreImpl(
                get(), //PoliticianService
                get() //PoliticianFactory()
            ) as RemotePoliticiansDataStore
        }

        bean {
            PoliticiansRepositoryImpl(
                this.androidApplication(),
                get(), //LocalPoliticiansDataStore
                get() //RemotePoliticiansDataStore
            ) as PoliticiansRepository
        }

        bean {
            PoliticianPresenter(
                get() //PoliticianRepository
            ) as PoliticianContract.Presenter
        }
    }
}