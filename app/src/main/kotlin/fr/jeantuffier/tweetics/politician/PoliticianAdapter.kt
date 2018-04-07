package fr.jeantuffier.tweetics.politician

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.model.politician.Politician
import fr.jeantuffier.tweetics.common.utils.picasso.CircleImage
import fr.jeantuffier.tweetics.politician.model.PoliticianClicked

class PoliticianAdapter(var politicians: List<Politician>) :
    RecyclerView.Adapter<PoliticianViewHolder>() {

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

        val politicianClicked = PoliticianClicked(
            politician.name,
            politician.screenName
        )
        holder.itemView.setOnClickListener { PoliticianOnItemClickHandler.onNext(politicianClicked) }

        loadProfileImage(holder, politician.getImageUrl())
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
