package fr.jeantuffier.tweetics.presentation.common.di

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import fr.jeantuffier.tweetics.presentation.politician.PoliticianActivity
import fr.jeantuffier.tweetics.presentation.politician.di.PoliticianActivityComponent
import fr.jeantuffier.tweetics.presentation.tweets.TweetsActivity
import fr.jeantuffier.tweetics.presentation.tweets.di.TweetsActivityComponent

@Module(subcomponents = [PoliticianActivityComponent::class, TweetsActivityComponent::class])
abstract class ActivitiesModule {

    @Binds
    @IntoMap
    @ActivityKey(PoliticianActivity::class)
    internal abstract fun bindsPoliticianActivityInjectorFactory(builder: PoliticianActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @Binds
    @IntoMap
    @ActivityKey(TweetsActivity::class)
    internal abstract fun bindsTweetActivityInjectorFactory(builder: TweetsActivityComponent.Builder): AndroidInjector.Factory<out Activity>

}