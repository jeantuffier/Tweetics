package fr.jeantuffier.tweetics.presentation.politician

import fr.jeantuffier.tweetics.data.datastore.politicians.LocalPoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.datastore.politicians.RemotePoliticiansDataStoreImpl
import fr.jeantuffier.tweetics.data.factory.PoliticianFactory
import fr.jeantuffier.tweetics.data.repository.PoliticiansRepositoryImpl
import fr.jeantuffier.tweetics.data.retrofit.service.PoliticianService
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.data.datastore.politicians.LocalPoliticiansDataStore
import fr.jeantuffier.tweetics.data.datastore.politicians.RemotePoliticiansDataStore
import fr.jeantuffier.tweetics.data.repository.PoliticiansRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit

object PoliticianModule {

    val module: Module = applicationContext {
        bean { PoliticianOnItemClickHandler(this.androidApplication()) }

        bean { PoliticianAdapter(get()) }

        bean { get<ApplicationDatabase>().politicianDao() }

        bean {
            LocalPoliticiansDataStoreImpl(
                get() //PoliticianDao
            ) as LocalPoliticiansDataStore
        }

        bean { get<Retrofit>().create(PoliticianService::class.java) }

        bean {
            RemotePoliticiansDataStoreImpl(
                get() //PoliticianService
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
