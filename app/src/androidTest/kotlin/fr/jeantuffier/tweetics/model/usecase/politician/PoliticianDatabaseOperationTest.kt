package fr.jeantuffier.tweetics.model.usecase.politician

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import fr.jeantuffier.tweetics.data.room.ApplicationDatabase
import fr.jeantuffier.tweetics.domain.model.Politician
import fr.jeantuffier.tweetics.domain.usecase.politician.GetLocalPoliticianUseCase
import fr.jeantuffier.tweetics.domain.usecase.politician.InsertPoliticianUseCase
import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec

class PoliticianDatabaseOperationTest : StringSpec({

}) /*{

    lateinit var applicationDatabase: ApplicationDatabase

    inner class DatabaseListener : TestListener {

        override fun beforeSpec(description: Description, spec: Spec) {
            super.beforeSpec(description, spec)
            applicationDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                ApplicationDatabase::class.java
            ).build()
        }

        override fun afterSpec(description: Description, spec: Spec) {
            super.afterSpec(description, spec)
            applicationDatabase.close()
        }
    }

    override fun listeners(): List<TestListener> = listOf(DatabaseListener())

    init {
        "politician should be inserted and fetched from db" {
            val politician = Politician(
                "user0",
                "description 0",
                "image 0",
                "name 0",
                "screen name 0"
            )

            val insertUseCase = InsertPoliticianUseCase(applicationDatabase.politicianDao())
            insertUseCase.insert(politician)

            val getUseCase = GetLocalPoliticianUseCase(applicationDatabase.politicianDao())
            val testObserver = getUseCase.get(politician.id)?.test()

            testObserver?.assertNoErrors()
            testObserver?.assertValue {
                it == politician
            }
        }
    }
}
*/
