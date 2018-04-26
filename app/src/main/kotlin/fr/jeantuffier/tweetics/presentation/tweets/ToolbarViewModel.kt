package fr.jeantuffier.tweetics.presentation.tweets

import android.widget.ImageView
import com.squareup.picasso.Picasso
import fr.jeantuffier.tweetics.presentation.common.Config
import fr.jeantuffier.tweetics.presentation.common.picasso.BlurImage
import fr.jeantuffier.tweetics.presentation.common.picasso.CircleImage
import javax.inject.Inject

class ToolbarViewModel @Inject constructor() {

    fun setBackgroundImage(imageView: ImageView, screenName: String) {
        Picasso.get()
            .load("${Config.TWEETICS_SERVER_IMAGE}/$screenName.jpg")
            .transform(
                BlurImage(
                    imageView.context,
                    25f
                )
            )
            .into(imageView)
    }

    fun setProfileImage(imageView: ImageView, screenName: String) {
        Picasso.get()
            .load("${Config.TWEETICS_SERVER_IMAGE}/$screenName.jpg")
            .transform(CircleImage())
            .into(imageView)
    }
}