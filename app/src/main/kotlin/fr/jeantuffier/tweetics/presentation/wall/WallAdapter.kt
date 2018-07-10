package fr.jeantuffier.tweetics.presentation.wall

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.tweet.TweetParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WallAdapter(
    private val tweetParser: TweetParser,
    private val wallOnItemClickHandler: WallOnItemClickHandler
) : RecyclerView.Adapter<WallViewHolder>() {

    var tweets: List<Tweet> = emptyList()

    override fun getItemCount() = tweets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.wall_item, parent, false)

        return WallViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallViewHolder, position: Int) {
        val tweet = tweets[position]

        holder.setDate(tweet.createdAt)
        setText(tweet, holder)
        holder.setMedias(tweet.medias)

        holder.itemView.setOnClickListener {
            wallOnItemClickHandler.onNext(tweet.getTweetUrl())
        }
    }

    private fun setText(tweet: Tweet, holder: WallViewHolder) {
        tweetParser.parse(tweet)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                holder.text.text = it
            }
    }

    override fun onViewRecycled(holder: WallViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

}