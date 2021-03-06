package fr.jeantuffier.tweetics.presentation.politician

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import fr.jeantuffier.tweetics.R

class PoliticianViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val image: ImageView = view.findViewById(R.id.image)
    val name: TextView = view.findViewById(R.id.name)
    val description: TextView = view.findViewById(R.id.description)

    fun clear() {
        image.setImageResource(0)
        name.text = ""
        description.text = ""
    }
}
