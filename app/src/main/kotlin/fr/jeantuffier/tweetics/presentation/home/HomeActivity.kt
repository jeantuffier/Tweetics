package fr.jeantuffier.tweetics.presentation.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import fr.jeantuffier.tweetics.R
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        viewPager.adapter = HomeAdapter(this.supportFragmentManager, this.resources)
        tabLayout.setupWithViewPager(viewPager)
    }

}
