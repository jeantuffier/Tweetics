package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.UserResponse
import fr.jeantuffier.tweetics.data.room.entities.UserEntity
import fr.jeantuffier.tweetics.domain.model.User

object UserFactory {

    fun mapToUser(response: UserResponse?): User {
        return User(
            response?.idStr ?: "",
            response?.name ?: "",
            response?.picture ?: "",
            response?.screenName ?: ""
        )
    }

    fun mapToEntity(user: User): UserEntity {
        return UserEntity(
            user.id,
            user.name,
            user.pictureUrl,
            user.screenName
        )
    }
}
