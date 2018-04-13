package fr.jeantuffier.tweetics.tweets.util

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import java.util.regex.Pattern

object TweetParser {

    private val urlPattern by lazy {
        Pattern.compile("(http|https)://(\\w+\\.)?(\\w+)\\.(\\w+)(/\\w+)?")
    }

    private class UrlSpan(private val url: String) : ClickableSpan() {
        override fun onClick(view: View) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            view.context.startActivity(intent)
        }

        override fun updateDrawState(paint: TextPaint) {
            super.updateDrawState(paint)
            paint.color = Color.BLUE
        }
    }

    fun parse(tweetText: String): SpannableString {
        return parseUrls(tweetText)
    }

    private fun parseUrls(tweetText: String): SpannableString {
        val baseSpan = SpannableString(tweetText)
        val matcher = urlPattern.matcher(tweetText)
        while (matcher.find()) {
            baseSpan.setSpan(
                UrlSpan(matcher.group()),
                matcher.start(),
                matcher.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return baseSpan
    }

}