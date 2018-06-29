package fr.jeantuffier.tweetics.presentation.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.presentation.politician.PoliticianFragment
import fr.jeantuffier.tweetics.presentation.tweets.TweetsFragment
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

    val fragments = listOf(PoliticianFragment(), TweetsFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
    }

}