package fr.jeantuffier.tweetics.data.mapper

import fr.jeantuffier.tweetics.data.retrofit.responses.PoliticianResponse
import fr.jeantuffier.tweetics.domain.mapper.Mapper
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet

class RemotePoliticianMapper : Mapper<PoliticianResponse, Politician> {

    var tweets : List<Tweet> = emptyList()

    override fun toRight(data: List<PoliticianResponse>): List<Politician> {
        return mutableListOf<Politician>().apply {
            data.forEach { add(toRight(it)) }
        }
    }

    override fun toRight(data: PoliticianResponse): Politician {
        return Politician(
            data.id,
            data.group.split(" "),
            data.name,
            data.role,
            data.screenName,
            tweets
        )
    }

}