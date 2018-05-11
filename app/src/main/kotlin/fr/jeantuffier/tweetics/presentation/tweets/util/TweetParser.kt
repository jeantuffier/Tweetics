package fr.jeantuffier.tweetics.presentation.tweets.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
import java.util.regex.Pattern
import javax.inject.Inject

class TweetParser @Inject constructor(private val context: Context) {

    private val twitterBlue by lazy { ContextCompat.getColor(context, R.color.twitterBlue) }

    private inner class UrlSpan(private val url: String) : ClickableSpan() {
        override fun onClick(view: View) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }

        override fun updateDrawState(paint: TextPaint) {
            super.updateDrawState(paint)
            paint.color = twitterBlue
        }
    }

    fun parse(tweet: Tweet): SpannableString {
        val text = tweet.fullText.substring(tweet.displayTextRange)
        val baseSpan = SpannableString(text)
        parseHashTags(tweet, baseSpan)
        parseUrls(tweet, baseSpan)
        parseUserMentions(tweet, baseSpan)

        return baseSpan
    }

    private fun parseHashTags(tweet: Tweet, baseSpan: SpannableString) {
        tweet.hashTags?.forEach {
            baseSpan.setSpan(
                UrlSpan("${Config.TWITTER_HASHTAG}/${it.text}"),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun parseUrls(tweet: Tweet, baseSpan: SpannableString) {
        tweet.urls?.forEach {
            baseSpan.setSpan(
                UrlSpan(it.url),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun parseUserMentions(tweet: Tweet, baseSpan: SpannableString) {
        tweet.userMentions?.forEach {
            baseSpan.setSpan(
                UrlSpan("${Config.TWITTER}/${it.screenName}"),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

}
