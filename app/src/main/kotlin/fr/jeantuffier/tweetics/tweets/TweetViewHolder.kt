package fr.jeantuffier.tweetics.tweets

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fr.jeantuffier.tweetics.R

class TweetViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    val image: ImageView = view.findViewById(R.id.image)
    val name: TextView = view.findViewById(R.id.name)
    val text: TextView = view.findViewById(R.id.text)

    fun clear() {
        image.setImageResource(0)
        name.text = ""
        text.text = ""
    }

}
