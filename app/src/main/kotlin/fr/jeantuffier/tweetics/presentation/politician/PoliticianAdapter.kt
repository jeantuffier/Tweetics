package fr.jeantuffier.tweetics.presentation.politician

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.presentation.common.Config
import fr.jeantuffier.tweetics.presentation.common.picasso.CircleImage
import javax.inject.Inject

class PoliticianAdapter @Inject constructor(
    private val politicianOnItemClickHandler: PoliticianOnItemClickHandler
) : RecyclerView.Adapter<PoliticianViewHolder>() {

    var politicians: MutableList<Politician> = mutableListOf()

    override fun getItemCount() = politicians.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoliticianViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.politicians_item_layout, parent, false)

        return PoliticianViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoliticianViewHolder, position: Int) {
        holder.clear()
        val politician = politicians[position]

        holder.itemView.setOnClickListener { politicianOnItemClickHandler.onNext(politician) }

        loadProfileImage(holder, "${Config.TWEETICS_SERVER_IMAGE}/${politician.screenName}.jpg")
        holder.name.text = politician.name
        holder.role.text = politician.role
    }

    private fun loadProfileImage(holder: PoliticianViewHolder, url: String) {
        Picasso.get()
            .load(url)
            .transform(CircleImage())
            .into(holder.image)
    }

}
