package fr.jeantuffier.tweetics.politician

import android.content.Context
import android.content.Intent
import fr.jeantuffier.tweetics.politician.model.PoliticianClicked
import fr.jeantuffier.tweetics.tweets.TweetActivity
import io.reactivex.subjects.PublishSubject

class PoliticianOnItemClickHandler(private val context: Context) {

    companion object {
        private val subject: PublishSubject<PoliticianClicked> =
            PublishSubject.create<PoliticianClicked>()

        fun onNext(politician: PoliticianClicked) {
            subject.onNext(politician)
        }
    }

    fun registerOnClickHandler() {
        subject.subscribe {
            val intent = Intent(context, TweetActivity::class.java).apply {
                putExtra(TweetActivity.TITLE, it.title)
                putExtra(TweetActivity.SCREEN_NAME, it.screenName)
            }
            context.startActivity(intent)
        }
    }
}