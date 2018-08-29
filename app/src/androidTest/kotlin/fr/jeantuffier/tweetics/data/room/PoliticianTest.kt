package fr.jeantuffier.tweetics.data.room

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import fr.jeantuffier.tweetics.data.room.entities.PoliticianEntity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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

    @Before
    fun initDb() {
        applicationDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            ApplicationDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Test
    fun insertPolitician() {
        val politician = PoliticianEntity(
            "user0",
            listOf("group1", "group2").toString(),
            "politician1",
            "path_to_picture",
            "role",
            "screen name"
        )

        applicationDatabase.politicianDao().insert(politician)
        val test = applicationDatabase.politicianDao().getPoliticians().blockingGet()

        assertEquals(politician, test[0])
    }

    @After
    fun closeDb() {
        applicationDatabase.close()
    }

}
