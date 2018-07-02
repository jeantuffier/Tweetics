package fr.jeantuffier.tweetics.presentation.politician

import android.content.Context
import android.content.Intent
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.presentation.tweets.TweetActivity
import io.reactivex.subjects.PublishSubject

class PoliticianOnItemClickHandler(private val context: Context) {

    private val subject by lazy { PublishSubject.create<Politician>() }

    init {
        subject.subscribe {
            val intent = Intent(context, TweetActivity::class.java).apply {
                putExtra(TweetActivity.TITLE, it.name)
                putExtra(TweetActivity.SCREEN_NAME, it.screenName)
            }
            context.startActivity(intent)
        }
    }

    fun onNext(politician: Politician) {
        subject.onNext(politician)
    }

}
