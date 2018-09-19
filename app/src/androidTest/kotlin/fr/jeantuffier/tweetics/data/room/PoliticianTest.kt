package fr.jeantuffier.tweetics.data.room

/*import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import fr.jeantuffier.tweetics.data.factory.PoliticianFactory
import fr.jeantuffier.tweetics.domain.model.Politician
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PoliticianTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var applicationDatabase: ApplicationDatabase

    private lateinit var

    @Before
    fun initDb() {
        applicationDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            ApplicationDatabase::class.java
        ).build()
    }

    @Test
    fun shouldInsertAndReadAPoliticianObject() {
        val politician = Politician(
            "user0",
            "description 0",
            "image 0",
            "name 0",
            "screen name 0"
        )

        applicationDatabase.politicianDao().insert(entity)
        val fetched = applicationDatabase.politicianDao().getPoliticians().blockingGet()

        val test = PoliticianFactory.mapEntitiesToPoliticians(fetched)

        assertEquals(politician, test[0])
    }

    @After
    fun closeDb() {
        applicationDatabase.close()
    }

}
*/
