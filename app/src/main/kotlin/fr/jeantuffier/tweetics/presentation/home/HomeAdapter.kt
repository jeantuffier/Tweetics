package fr.jeantuffier.tweetics.presentation.home

import android.content.res.Resources
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.presentation.politician.PoliticianFragment
import fr.jeantuffier.tweetics.presentation.wall.WallFragment

class HomeAdapter(
    fragmentManager: FragmentManager,
    private val resources: Resources
) : FragmentPagerAdapter(fragmentManager) {

    private val items = listOf(WallFragment(), PoliticianFragment())

    override fun getItem(position: Int) = items[position]

    override fun getCount() = items.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> resources.getString(R.string.home_tab_wall)
            else -> resources.getString(R.string.home_tab_politicians)
        }
    }

}
