package fr.jeantuffier.tweetics.presentation.tweets.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class TweetsOnItemClickHandler @Inject constructor(private val context: Context) {

    private val subject by lazy { PublishSubject.create<String>() }

    init {
        subject.subscribe {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            context.startActivity(intent)
        }
    }

    fun onNext(url: String) {
        subject.onNext(url)
    }

}
