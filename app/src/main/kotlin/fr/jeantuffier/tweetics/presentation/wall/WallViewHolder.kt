package fr.jeantuffier.tweetics.presentation.wall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.View
import android.widget.TextView
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.presentation.common.picasso.CircleImage
import java.text.SimpleDateFormat
import java.util.*

private const val MEDIA_TYPE_PHOTO = "photo"
private const val MEDIA_TYPE_VIDEO = "video"

class WallViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val profile: AppCompatImageView = view.findViewById(R.id.profile)
    val name: TextView = view.findViewById(R.id.name)
    val date: TextView = view.findViewById(R.id.date)
    val text: AppCompatTextView = view.findViewById(R.id.text)
    val media: AppCompatImageView = view.findViewById(R.id.media)
    val play: AppCompatImageView = view.findViewById(R.id.play)

    fun setProfilePicture(url: String) {
        Picasso.get()
            .load(url)
            .transform(CircleImage())
            .into(profile)
    }

    fun setHeader(name: String, date: String) {
        this.name.text = name
        this.date.text = getDisplayDate(date)
    }

    private fun getDisplayDate(date: String): String {
        val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.getDefault())
        val parsedDate = formatter.parse(date)

        val dateFormat = DateFormat.getLongDateFormat(itemView.context)
        val timeFormat = DateFormat.getTimeFormat(itemView.context)
        return "${dateFormat.format(parsedDate)} ${timeFormat.format(parsedDate)}"
    }

    fun setMedias(medias: List<Media>) {
        val videoMedia = medias.firstOrNull { it.type == MEDIA_TYPE_VIDEO }
        val photoMedia = medias.firstOrNull { it.type == MEDIA_TYPE_PHOTO }

        if (videoMedia != null) {
            val videoUrl = videoMedia.videoInfo?.url
            val placeHolderUrl = videoMedia.url
            videoMedia.videoInfo?.url?.let { displayVideo(videoUrl, placeHolderUrl) }
        } else if (photoMedia != null) {
            displayPhoto(photoMedia.url)
        }
    }

    private fun displayVideo(videoUrl: String?, placeholderUrl: String) {
        videoUrl?.let { path ->
            play.visibility = View.VISIBLE
            media.visibility = View.VISIBLE
            Picasso.get()
                .load(placeholderUrl)
                .into(media)
            media.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(path))
                ContextCompat.startActivity(it.context, intent, Bundle())
            }
        }
    }

    private fun displayPhoto(url: String) {
        media.visibility = View.VISIBLE
        Picasso.get()
            .load(url)
            .into(media)
    }

    fun clear() {
        date.text = ""
        text.text = ""
        media.setImageResource(0)
        media.visibility = View.GONE
        play.visibility = View.GONE
    }

}
