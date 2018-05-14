package fr.jeantuffier.tweetics.presentation.tweets.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.view.View
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.Config
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

    fun parse(tweet: Tweet): CharSequence {
        return if (tweet.reTweet == null) {
            getSpannableTweet(tweet)
        } else {
            val prefix = getReTweetPrefix(tweet)
            val content = getSpannableTweet(tweet.reTweet)
            TextUtils.concat(prefix, content)
        }
    }

    private fun getSpannableTweet(tweet: Tweet): SpannableString {
        val text = getContent(tweet)
        val baseSpan = SpannableString(text)

        parseHashTags(tweet.hashTags, baseSpan)
        parseUrls(
            tweet.urls?.filter { it.indices.first() < tweet.displayTextRange.endInclusive },
            baseSpan
        )
        parseUserMentions(tweet.userMentions, baseSpan)

        return baseSpan
    }

    private fun getContent(tweet: Tweet) =
        tweet.fullText.substring(tweet.displayTextRange.start, tweet.displayTextRange.endInclusive)

    private fun getReTweetPrefix(tweet: Tweet): SpannableString {
        if (tweet.reTweet == null) return SpannableString("")

        val text = "RT @${tweet.userMentions!!.first().screenName}: "
        val prefixSpan = SpannableString(text)
        parseUserMentions(
            tweet.userMentions.filter { it.indices.endInclusive <= text.length },
            prefixSpan
        )

        return prefixSpan
    }

    private fun parseHashTags(hashTags: List<Link.HashTag>?, baseSpan: SpannableString) {
        hashTags?.forEach {
            baseSpan.setSpan(
                UrlSpan("${Config.TWITTER_HASHTAG}/${it.text}"),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun parseUrls(urls: List<Link.Url>?, baseSpan: SpannableString) {
        urls?.forEach {
            baseSpan.setSpan(
                UrlSpan(it.url),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun parseUserMentions(
        userMentions: List<Link.UserMention>?,
        baseSpan: SpannableString
    ) {
        userMentions?.forEach {
            baseSpan.setSpan(
                UrlSpan("${Config.TWITTER}/${it.screenName}"),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

}
