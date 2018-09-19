package fr.jeantuffier.tweetics.data.room

/*import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import fr.jeantuffier.tweetics.data.factory.LinkFactory
import fr.jeantuffier.tweetics.data.factory.PoliticianFactory
import fr.jeantuffier.tweetics.data.factory.TweetFactory
import fr.jeantuffier.tweetics.domain.model.Link
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.model.Tweet
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TweetTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationDatabase: ApplicationDatabase

    @Before
    fun initDb() {
        applicationDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            ApplicationDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Test
    fun shouldInsertAndReadALinkObject() {
        val politician = getPolitician()
        insertPolitician(politician)

        val tweet = Tweet(
            "tweet0",
            "created at",
            "full text",
            null,
            emptyList(),
            emptyList(),
            IntRange(0, 100),
            politician
        )

        val entities = TweetFactory.mapToEntities(listOf(tweet))
        applicationDatabase.tweetDao().insertAll(entities)

        val fetched = applicationDatabase.tweetDao().getTweets(politician.id).blockingGet()
        val test = TweetFactory.mapToTweets(fetched, politician)

        Assert.assertEquals(tweet, test[0])
    }

    private fun getPolitician(): Politician {
        return Politician(
            "user0",
            listOf("group1", "group2"),
            "politician1",
            "path_to_picture",
            "role",
            "screen name"
        )
    }

    private fun insertPolitician(politician: Politician) {
        val entity = PoliticianFactory.mapPoliticianToEntity(politician)
        applicationDatabase.politicianDao().insert(entity)
    }

    @After
    fun closeDb() {
        applicationDatabase.close()
    }

}
*/
