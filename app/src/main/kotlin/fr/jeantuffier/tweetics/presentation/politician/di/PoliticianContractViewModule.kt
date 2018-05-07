package fr.jeantuffier.tweetics.presentation.politician.di

import dagger.Binds
import dagger.Module
import fr.jeantuffier.tweetics.presentation.politician.PoliticianActivity
import fr.jeantuffier.tweetics.presentation.politician.PoliticianContract

@Module
abstract class PoliticianContractViewModule {

    @Binds
    abstract fun bindsPoliticianActivity(activity: PoliticianActivity): PoliticianContract.View

}
