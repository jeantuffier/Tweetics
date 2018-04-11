package fr.jeantuffier.tweetics.tweets

import android.widget.ImageView
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.common.Config
import fr.jeantuffier.tweetics.common.utils.picasso.BlurImage
import javax.inject.Inject

class CollapsingToolbarViewModel @Inject constructor() {

    fun setImage(imageView: ImageView, screenName: String) {
        Picasso.get()
            .load("${Config.IMAGE_BASE_URL}$screenName.jpg")
            .transform(BlurImage(imageView.context, 15f))
            .into(imageView)
    }
}