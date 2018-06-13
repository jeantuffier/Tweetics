package fr.jeantuffier.tweetics.presentation.tweets

import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import android.widget.VideoView
import fr.jeantuffier.tweetics.R

class TweetsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val date: TextView = view.findViewById(R.id.date)
    val text: AppCompatTextView = view.findViewById(R.id.text)
    val media: AppCompatImageView = view.findViewById(R.id.media)

    fun clear() {
        date.text = ""
        text.text = ""
        media.setImageResource(0)
        media.visibility = View.GONE
    }

}
