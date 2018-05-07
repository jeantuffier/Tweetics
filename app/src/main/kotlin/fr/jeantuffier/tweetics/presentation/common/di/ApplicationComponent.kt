package fr.jeantuffier.tweetics.presentation.common.di

import dagger.Component
import dagger.android.AndroidInjector
import fr.jeantuffier.tweetics.presentation.common.Tweetics
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ActivitiesModule::class,
        ApplicationModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<Tweetics>