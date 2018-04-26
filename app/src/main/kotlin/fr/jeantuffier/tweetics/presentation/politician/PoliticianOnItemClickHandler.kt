package fr.jeantuffier.tweetics.presentation.politician

import android.content.Context
import android.content.Intent
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.presentation.tweets.TweetActivity
import io.reactivex.subjects.PublishSubject

class PoliticianOnItemClickHandler(private val context: Context) {

    companion object {
        private val subject: PublishSubject<Politician> =
            PublishSubject.create<Politician>()

        fun onNext(politician: Politician) {
            subject.onNext(politician)
        }
    }

    fun registerOnClickHandler() {
        subject.subscribe {
            val intent = Intent(context, TweetActivity::class.java).apply {
                putExtra(TweetActivity.TITLE, it.name)
                putExtra(TweetActivity.SCREEN_NAME, it.screenName)
            }
            context.startActivity(intent)
        }
    }

}
