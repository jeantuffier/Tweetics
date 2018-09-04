package fr.jeantuffier.tweetics.data.room

import android.arch.core.executor.testing.InstantTaskExecutorRule
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
class LinkTest {

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

        val tweet = getTweet(politician)
        insertTweet(tweet)

        val link = Link(
            "link0",
            tweet.id,
            "link test",
            IntRange(0, 50),
            Link.Companion.LinkType.URL
        )

        val entity = LinkFactory.mapToEntity(link)

        applicationDatabase.linkDao().insert(entity)
        val fetched = applicationDatabase.linkDao().getLinks(tweet.id).blockingGet()

        val test = LinkFactory.mapToLinks(tweet.id, fetched)

        Assert.assertEquals(link, test[0])
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

    private fun getTweet(politician: Politician): Tweet {
        return Tweet(
            "tweet0",
            "created at",
            "full text",
            null,
            emptyList(),
            emptyList(),
            IntRange(0, 100),
            politician
        )
    }

    private fun insertTweet(tweet: Tweet) {
        val entities = TweetFactory.mapToEntities(listOf(tweet))
        applicationDatabase.tweetDao().insertAll(entities)
    }

    @After
    fun closeDb() {
        applicationDatabase.close()
    }

}
