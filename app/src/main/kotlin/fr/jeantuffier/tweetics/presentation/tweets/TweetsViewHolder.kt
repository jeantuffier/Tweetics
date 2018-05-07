package fr.jeantuffier.tweetics.presentation.tweets

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import fr.jeantuffier.tweetics.R

class TweetsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val date: TextView = view.findViewById(R.id.date)
    val text: TextView = view.findViewById(R.id.text)

    fun clear() {
        date.text = ""
        text.text = ""
    }

}
