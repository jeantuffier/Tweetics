package fr.jeantuffier.tweetics.presentation.politician

import android.content.Context
import android.content.Intent
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.presentation.tweets.TweetsActivity
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class PoliticianOnItemClickHandler @Inject constructor(private val context: Context) {

    private val subject by lazy { PublishSubject.create<Politician>() }

    init {
        subject.subscribe {
            val intent = Intent(context, TweetsActivity::class.java).apply {
                putExtra(TweetsActivity.TITLE, it.name)
                putExtra(TweetsActivity.SCREEN_NAME, it.screenName)
            }
            context.startActivity(intent)
        }
    }

    fun onNext(politician: Politician) {
        subject.onNext(politician)
    }

}
