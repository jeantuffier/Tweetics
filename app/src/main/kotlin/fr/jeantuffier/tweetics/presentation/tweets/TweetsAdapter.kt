package fr.jeantuffier.tweetics.presentation.tweets

import android.content.Context
import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.tweets.util.TweetParser
import fr.jeantuffier.tweetics.presentation.tweets.util.TweetsOnItemClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.os.Bundle


private const val MEDIA_TYPE_PHOTO = "photo"
private const val MEDIA_TYPE_VIDEO = "video"

class TweetsAdapter @Inject constructor(
    private val tweetParser: TweetParser,
    private val tweetsOnItemClickHandler: TweetsOnItemClickHandler
) : RecyclerView.Adapter<TweetsViewHolder>() {

    var tweets: List<Tweet> = emptyList()

    private val mediaPlayer by lazy { MediaPlayer() }

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
        holder.date.text = getDisplayDate(holder.itemView.context, tweet.createdAt)
        setText(tweet, holder)
        setMedias(holder, tweet.medias)

        holder.itemView.setOnClickListener {
            tweetsOnItemClickHandler.onNext(tweet.getTweetUrl())
        }
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

    private fun setMedias(holder: TweetsViewHolder, medias: List<Media>) {
        val videoMedia = medias.firstOrNull { it.type == MEDIA_TYPE_VIDEO}
        val photoMedia = medias.firstOrNull { it.type == MEDIA_TYPE_PHOTO}

        if (videoMedia != null) {
            val videoUrl = videoMedia.videoInfo?.url
            val placeHolderUrl = videoMedia.url
            videoMedia.videoInfo?.url?.let { displayVideo(holder, videoUrl, placeHolderUrl) }
        } else if (photoMedia != null ) {
            displayPhoto(holder, photoMedia.url)
        }
    }

    private fun displayVideo(holder: TweetsViewHolder, videoUrl: String?, placeholderUrl: String) {
        videoUrl?.let { path ->
            holder.play.visibility = View.VISIBLE
            holder.media.visibility = View.VISIBLE
            Picasso.get()
                .load(placeholderUrl)
                .into(holder.media)
            holder.media.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(path))
                startActivity(it.context, intent, Bundle())
            }
        }
    }

    private fun displayPhoto(holder: TweetsViewHolder, url: String) {
        holder.media.visibility = View.VISIBLE
        Picasso.get()
            .load(url)
            .into(holder.media)
    }
}
