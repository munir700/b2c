package co.yap.household.dashboard.main

import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import co.yap.household.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.setUpWithViewPager(viewpager: ViewPager) {
    setOnNavigationItemSelectedListener { viewpager.onNavigationItemSelected(it) }
    setOnNavigationItemReselectedListener(::onNavigationItemReselected)
}

private fun ViewPager.onNavigationItemSelected(item: MenuItem): Boolean {
    setCurrentItem(getPageIndexForMenuItem(item), false)
    return true
}

private fun getPageIndexForMenuItem(item: MenuItem): Int {
    return when (item.itemId) {
        R.id.item_Home -> 0
        R.id.item_expenses -> 1
        R.id.item_my_card -> 2
        R.id.item_more -> 3
        else -> -1
    }
}

private fun onNavigationItemReselected(item: MenuItem) {
}