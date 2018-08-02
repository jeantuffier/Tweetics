package fr.jeantuffier.tweetics.data.factory

import fr.jeantuffier.tweetics.data.retrofit.responses.UserResponse
import fr.jeantuffier.tweetics.domain.model.User

object UserFactory {

    fun getUserFromRemote(response: UserResponse?): User {
        return User(
            response?.idStr ?: "",
            response?.name ?: "",
            response?.picture ?: ""
        )
    }

    private fun getUserFromLocal(userId: String): User {
        return User("", "", "")
    }
}
