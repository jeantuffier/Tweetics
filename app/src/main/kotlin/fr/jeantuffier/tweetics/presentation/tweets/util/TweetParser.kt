package fr.jeantuffier.tweetics.presentation.tweets.util

import android.content.Context
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
import io.reactivex.Observable

class TweetParser(private val context: Context) {

    private val twitterBlue by lazy { ContextCompat.getColor(context, R.color.twitterBlue) }

    private inner class UrlSpan(private val url: String) : ClickableSpan() {
        override fun onClick(view: View) {}

        override fun updateDrawState(paint: TextPaint) {
            super.updateDrawState(paint)
            paint.color = twitterBlue
        }
    }

    fun parse(tweet: Tweet): Observable<CharSequence> {
        return Observable.fromCallable {
            val prefix = getReTweetPrefix(tweet)
            val content = getSpannableTweet(tweet)
            TextUtils.concat(prefix, content)
        }
    }

    private fun getReTweetPrefix(tweet: Tweet): SpannableString {
        if (tweet.reTweet == null) return SpannableString("")

        val prefixSpan = SpannableString(tweet.reTweetUserMention)
        val mentions = filterLink(tweet.userMentions, tweet.reTweetUserMention.length)
        parseLink(mentions, prefixSpan)

        return prefixSpan
    }

    private fun getSpannableTweet(tweet: Tweet): SpannableString {
        val tweetToUse = tweet.reTweet ?: tweet
        val baseSpan = SpannableString(tweetToUse.content)

        val urlsLink = filterLink(tweetToUse.urls, tweetToUse.displayTextRange.endInclusive)

        parseLink(tweetToUse.hashTags, baseSpan)
        parseLink(urlsLink, baseSpan)
        parseLink(tweetToUse.userMentions, baseSpan)

        return baseSpan
    }

    private fun filterLink(links: List<Link>?, indexMax: Int) =
        links?.filter { it.indices.endInclusive <= indexMax }

    private fun parseLink(links: List<Link>?, baseSpan: SpannableString) {
        links?.forEach {
            baseSpan.setSpan(
                UrlSpan(getLinkUrl(it)),
                it.indices.first(),
                it.indices.last(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun getLinkUrl(link: Link): String {
        return when (link.type) {
            Link.Companion.LinkType.HASH_TAG -> "${Config.TWITTER_HASHTAG}/${link.text}"
            Link.Companion.LinkType.URL -> link.text
            Link.Companion.LinkType.USER_MENTION -> "${Config.TWITTER}/${link.text}"
        }
    }

}
