package fr.jeantuffier.tweetics.common.di

import dagger.Component
import fr.jeantuffier.tweetics.politician.PoliticianActivity
import fr.jeantuffier.tweetics.tweets.TweetActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(politicianActivity: PoliticianActivity)

    fun inject(tweetActivity: TweetActivity)
}