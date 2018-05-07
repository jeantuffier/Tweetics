package fr.jeantuffier.tweetics.presentation.common

import android.app.Activity
import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import fr.jeantuffier.tweetics.presentation.common.di.ApplicationModule
import fr.jeantuffier.tweetics.presentation.common.di.DaggerApplicationComponent
import javax.inject.Inject

class Tweetics : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
            .inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}