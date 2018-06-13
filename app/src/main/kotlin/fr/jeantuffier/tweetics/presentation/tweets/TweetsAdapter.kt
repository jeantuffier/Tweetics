package fr.jeantuffier.tweetics.presentation.tweets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.tweets.util.TweetParser
import fr.jeantuffier.tweetics.presentation.tweets.util.TweetsOnItemClickHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class TweetsAdapter @Inject constructor(
    private val tweetParser: TweetParser,
    private val tweetsOnItemClickHandler: TweetsOnItemClickHandler
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
        val tweet = tweets[position]

        holder.setDate(tweet.createdAt)
        setText(tweet, holder)
        holder.setMedias(tweet.medias)

        holder.itemView.setOnClickListener {
            tweetsOnItemClickHandler.onNext(tweet.getTweetUrl())
        }
    }

    private fun setText(tweet: Tweet, holder: TweetsViewHolder) {
        tweetParser.parse(tweet)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                holder.text.text = it
            }
    }

    override fun onViewRecycled(holder: TweetsViewHolder) {
        super.onViewRecycled(holder)
        holder.clear()
    }

}
