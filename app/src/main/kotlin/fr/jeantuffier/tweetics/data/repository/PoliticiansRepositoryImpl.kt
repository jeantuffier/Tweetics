package fr.jeantuffier.tweetics.data.repository

import android.content.Context
import fr.jeantuffier.tweetics.domain.datastore.LocalPoliticiansDataStore
import fr.jeantuffier.tweetics.domain.datastore.RemotePoliticiansDataStore
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.repositories.PoliticiansRepository
import io.reactivex.Single
import javax.inject.Inject

private const val POLITICIAN_PREFERENCES = "politician_preferences"
private const val POLITICIAN_UPDATE = "politician_update"

class PoliticiansRepositoryImpl @Inject constructor(
    private val context: Context,
    private val localDataStore: LocalPoliticiansDataStore,
    private val remoteDataStore: RemotePoliticiansDataStore
) : PoliticiansRepository {

    override fun getPoliticians(): Single<List<Politician>> {
        return localDataStore.getPoliticians()
            .flatMap { politicians ->
                if (shouldLoadFromApi(getLastUpdate(), politicians)) {
                    getRemotePoliticians()
                } else {
                    Single.just(politicians)
                }
            }
    }

    private fun shouldLoadFromApi(timestamp: Long, politicians: List<Politician>) =
        isMoreThanTenMinutesSinceLastUpdate(timestamp) || politicians.isEmpty()

    private fun isMoreThanTenMinutesSinceLastUpdate(timeStamp: Long): Boolean {
        return (timeStamp - System.currentTimeMillis()) > 10 * 60 * 1000
    }

    private fun getLastUpdate(): Long {
        return context
            .getSharedPreferences(POLITICIAN_PREFERENCES, Context.MODE_PRIVATE)
            .getLong(POLITICIAN_UPDATE, 0)
    }

    private fun getRemotePoliticians(): Single<List<Politician>> {
        return remoteDataStore
            .getPoliticians()
            .doOnSuccess { savePoliticians(it) }
    }

    private fun savePoliticians(politicians: List<Politician>) {
        localDataStore
            .savePoliticians(politicians) {
                setLastUpdate()
            }
    }

    private fun setLastUpdate() {
        context
            .getSharedPreferences(POLITICIAN_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putLong(POLITICIAN_UPDATE, System.currentTimeMillis())
            .apply()
    }

}
