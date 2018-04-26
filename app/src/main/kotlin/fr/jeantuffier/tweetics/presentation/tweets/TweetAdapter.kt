package fr.jeantuffier.tweetics.presentation.tweets

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.data.room.entities.TweetEntity
import fr.jeantuffier.tweetics.presentation.tweets.util.TweetParser
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TweetAdapter @Inject constructor(
    private val tweetParser: TweetParser
) : RecyclerView.Adapter<TweetViewHolder>() {

    var tweets: List<TweetEntity> = emptyList()

    override fun getItemCount() = tweets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.tweets_item_layout, parent, false)

        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.clear()
        val tweet = tweets[position]

        holder.date.text = getDisplayDate(holder.itemView.context, tweet.createdAt)
        holder.text.text = tweetParser.parse(tweet.fullText)
        holder.text.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun getDisplayDate(context: Context, date: String): String {
        val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.getDefault())
        val parsedDate = formatter.parse(date)

        val dateFormat = DateFormat.getLongDateFormat(context)
        val timeFormat = DateFormat.getTimeFormat(context)
        return "${dateFormat.format(parsedDate)} ${timeFormat.format(parsedDate)}"
    }

}
