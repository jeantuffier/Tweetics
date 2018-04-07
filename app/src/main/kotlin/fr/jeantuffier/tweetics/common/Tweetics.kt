package fr.jeantuffier.tweetics.common

import android.app.Application
import fr.jeantuffier.tweetics.common.di.ApplicationComponent
import fr.jeantuffier.tweetics.common.di.ApplicationModule
import fr.jeantuffier.tweetics.common.di.DaggerApplicationComponent

class Tweetics : Application() {

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }
}