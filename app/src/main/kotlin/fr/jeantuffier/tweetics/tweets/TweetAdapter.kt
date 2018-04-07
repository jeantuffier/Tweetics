package fr.jeantuffier.tweetics.tweets

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.model.tweet.TweetWrapper
import fr.jeantuffier.tweetics.common.utils.picasso.CircleImage

class TweetAdapter(var tweetWrapper: TweetWrapper) : RecyclerView.Adapter<TweetViewHolder>() {

    override fun getItemCount() = tweetWrapper.tweets?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.tweets_item_layout, parent, false)

        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.clear()
        tweetWrapper.tweets?.get(position)?.let {
            //val politicianClicked = PoliticianClicked(politician.name, politician.screenName)
            //holder.itemView.setOnClickListener { PoliticianOnItemClickHandler.onNext(politicianClicked) }

            loadProfileImage(holder, it.getImageUrl())
            holder.name.text = it.screenName
            holder.text.text = it.fullText
        }
    }

    private fun loadProfileImage(holder: TweetViewHolder, url: String) {
        Picasso.get()
            .load(url)
            .transform(CircleImage())
            .into(holder.image)
    }
}
