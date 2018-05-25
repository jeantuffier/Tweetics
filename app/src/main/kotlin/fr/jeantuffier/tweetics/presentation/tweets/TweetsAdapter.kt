package fr.jeantuffier.tweetics.presentation.tweets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.tweets.util.TweetParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TweetsAdapter @Inject constructor(
    private val tweetParser: TweetParser
) : RecyclerView.Adapter<TweetsViewHolder>() {

    var tweets: List<Tweet> = emptyList()

    override fun getItemCount() = tweets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.tweets_item_layout, parent, false)

        return TweetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        holder.clear()
        val tweet = tweets[position]
        setText(tweet, holder)
        holder.date.text = getDisplayDate(holder.itemView.context, tweet.createdAt)
        holder.text.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun getDisplayDate(context: Context, date: String): String {
        val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.getDefault())
        val parsedDate = formatter.parse(date)

        val dateFormat = DateFormat.getLongDateFormat(context)
        val timeFormat = DateFormat.getTimeFormat(context)
        return "${dateFormat.format(parsedDate)} ${timeFormat.format(parsedDate)}"
    }

    private fun setText(tweet: Tweet, holder: TweetsViewHolder) {
        tweetParser.parse(tweet)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                holder.text.text = it
            }
    }

}
