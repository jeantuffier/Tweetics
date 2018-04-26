package fr.jeantuffier.tweetics.presentation.politician.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import fr.jeantuffier.tweetics.presentation.politician.PoliticianActivity
import javax.inject.Singleton

@PoliticianActivityScope
@Subcomponent(modules = [
    PoliticianContractPresenterModule::class,
    PoliticianContractViewModule::class
])
interface PoliticianActivityComponent : AndroidInjector<PoliticianActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<PoliticianActivity>()

}