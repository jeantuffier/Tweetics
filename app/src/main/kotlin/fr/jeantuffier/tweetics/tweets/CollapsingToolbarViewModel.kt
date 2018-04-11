package fr.jeantuffier.tweetics.tweets

import android.widget.ImageView
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.common.Config
import javax.inject.Inject

class CollapsingToolbarViewModel @Inject constructor() {

    fun setImage(imageView: ImageView, screenName: String) {
        Picasso.get()
            .load("${Config.IMAGE_BASE_URL}$screenName.jpg")
            .into(imageView)
    }
}