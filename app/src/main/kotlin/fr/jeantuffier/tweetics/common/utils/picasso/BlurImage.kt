package fr.jeantuffier.tweetics.common.utils.picasso

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.squareup.picasso.Transformation


class BlurImage(
    private val context: Context,
    private val radius: Float = 1f,
    private val scale: Double = 1.toDouble()
) : Transformation {

    override fun key() = "BlurImage"

    override fun transform(source: Bitmap): Bitmap {
        val width = Math.round(source.width * scale).toInt()
        val height = Math.round(source.height * scale).toInt()

        val inputBitmap = Bitmap.createScaledBitmap(source, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)
        val theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        theIntrinsic.setRadius(radius)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }

}
