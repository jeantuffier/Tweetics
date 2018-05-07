package fr.jeantuffier.tweetics.presentation.tweets.di

import dagger.Binds
import dagger.Module
import fr.jeantuffier.tweetics.presentation.tweets.TweetsActivity
import fr.jeantuffier.tweetics.presentation.tweets.TweetsContract

@Module
abstract class TweetsContractViewModule {

    @Binds
    abstract fun bindsTweetsActivity(activity: TweetsActivity): TweetsContract.View

}
