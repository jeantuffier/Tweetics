package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.MediaResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.SizeResponse
import fr.jeantuffier.tweetics.data.retrofit.responses.SizesResponse
import fr.jeantuffier.tweetics.domain.model.Media
import fr.jeantuffier.tweetics.domain.model.Size
import fr.jeantuffier.tweetics.domain.model.VideoInfo
import java.util.*

private const val VIDEO_CONTENT_TYPE = "application/x-mpegURL"

object MediaFactory {

    fun getMedias(medias: List<MediaResponse>?): List<Media> {
        return medias?.map {
            Media(
                it.id,
                it.url,
                it.type,
                getSizes(it.sizes),
                it.videoInfo?.let {
                    it.variants.firstOrNull { it.contentType == VIDEO_CONTENT_TYPE }?.let {
                        VideoInfo(it.contentType, it.url)
                    }
                }
            )
        } ?: emptyList()
    }

    private fun getSizes(response: SizesResponse): List<Size> {
        return mutableListOf<Size>().apply {
            add(getSize(response.thumb, Size.Companion.Type.THUMB))
            add(getSize(response.small, Size.Companion.Type.SMALL))
            add(getSize(response.medium, Size.Companion.Type.MEDIUM))
            add(getSize(response.large, Size.Companion.Type.LARGE))
        }
    }

    private fun getSize(response: SizeResponse, type: Size.Companion.Type) =
        Size(UUID.randomUUID().toString(), response.width, response.height, response.resize, type)

}
