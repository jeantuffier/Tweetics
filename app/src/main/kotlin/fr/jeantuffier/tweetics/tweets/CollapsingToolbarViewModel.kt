package fr.jeantuffier.tweetics.tweets

import android.widget.ImageView
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.common.Config
import fr.jeantuffier.tweetics.common.utils.picasso.BlurImage
import fr.jeantuffier.tweetics.common.utils.picasso.CircleImage
import javax.inject.Inject

class CollapsingToolbarViewModel @Inject constructor() {

    fun setBackgroundImage(imageView: ImageView, screenName: String) {
        Picasso.get()
            .load("${Config.IMAGE_BASE_URL}$screenName.jpg")
            .transform(BlurImage(imageView.context, 25f))
            .into(imageView)
    }

    fun setProfileImage(imageView: ImageView, screenName: String) {
        Picasso.get()
            .load("${Config.IMAGE_BASE_URL}$screenName.jpg")
            .transform(CircleImage())
            .into(imageView)
    }
}