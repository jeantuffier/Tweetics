package fr.jeantuffier.tweetics.presentation.politician

import android.content.Context
import android.content.Intent
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.presentation.tweets.TweetsFragment
import io.reactivex.subjects.PublishSubject

class PoliticianOnItemClickHandler(private val context: Context) {

    private val subject by lazy { PublishSubject.create<Politician>() }

    init {
        subject.subscribe {
            val intent = Intent(context, TweetsFragment::class.java).apply {
                putExtra(TweetsFragment.TITLE, it.name)
                putExtra(TweetsFragment.SCREEN_NAME, it.screenName)
            }
            context.startActivity(intent)
        }
    }

    fun onNext(politician: Politician) {
        subject.onNext(politician)
    }

}
