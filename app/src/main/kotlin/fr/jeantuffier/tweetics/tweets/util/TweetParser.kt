package fr.jeantuffier.tweetics.tweets.util

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
import fr.jeantuffier.tweetics.common.Config
import java.util.regex.Pattern
import javax.inject.Inject

class TweetParser @Inject constructor(private val context: Context) {

    private val urlPattern by lazy {
        Pattern.compile("(http|https)://(\\w+\\.)?(\\w+)\\.(\\w+)(/\\w+)?")
    }

    private val hashTagPattern by lazy {
        Pattern.compile("#\\w+")
    }

    private val userPattern by lazy {
        Pattern.compile("@\\w+")
    }

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

    fun parse(tweetText: String): SpannableString {
        val baseSpan = SpannableString(tweetText)
        parseLink(baseSpan, urlPattern)
        parseLink(baseSpan, hashTagPattern)
        parseLink(baseSpan, userPattern)

        return baseSpan
    }

    private fun parseLink(baseSpan: SpannableString, pattern: Pattern, isUrl: Boolean = false) {
        val matcher = pattern.matcher(baseSpan)
        while (matcher.find()) {
            val url = when (isUrl) {
                true -> matcher.group()
                false -> "${Config.TWITTER}/${matcher.group()}"
            }
            baseSpan.setSpan(
                UrlSpan(url),
                matcher.start(),
                matcher.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

}
