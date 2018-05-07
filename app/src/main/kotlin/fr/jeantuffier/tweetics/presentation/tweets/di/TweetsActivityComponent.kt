package fr.jeantuffier.tweetics.presentation.tweets.di

import dagger.Subcomponent
import dagger.android.AndroidInjector
import fr.jeantuffier.tweetics.presentation.tweets.TweetsActivity

@TweetsActivityScope
@Subcomponent(
    modules = [
        TweetsContractPresenterModule::class,
        TweetsContractViewModule::class
    ]
)
interface TweetsActivityComponent : AndroidInjector<TweetsActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<TweetsActivity>()

}
