package fr.jeantuffier.tweetics.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import fr.jeantuffier.tweetics.presentation.politician.PoliticianActivity
import fr.jeantuffier.tweetics.presentation.politician.di.PoliticianActivityComponent

@Module(subcomponents = [PoliticianActivityComponent::class])
abstract class ActivitiesModule {

    @Binds
    @IntoMap
    @ActivityKey(PoliticianActivity::class)
    internal abstract fun bindsPoliticianActivityInjectorFactory(builder: PoliticianActivityComponent.Builder): AndroidInjector.Factory<out Activity>

}