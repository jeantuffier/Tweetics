package fr.jeantuffier.tweetics.tweets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import fr.jeantuffier.tweetics.common.utils.picasso.CircleImage

class TweetAdapter(var tweets: List<Tweet>) : RecyclerView.Adapter<TweetViewHolder>() {

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

        loadProfileImage(holder, tweet.getImageUrl())
        holder.name.text = tweet.screenName
        holder.text.text = tweet.fullText
    }

    private fun loadProfileImage(holder: TweetViewHolder, url: String) {
        Picasso.get()
            .load(url)
            .transform(CircleImage())
            .into(holder.image)
    }
}
