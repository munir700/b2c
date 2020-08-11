package co.yap.household.dashboard.main

import android.view.View
import androidx.drawerlayout.widget.DrawerLayout

interface DrawerListenerImpl:DrawerLayout.DrawerListener {
    override fun onDrawerStateChanged(newState: Int) {

    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
    }

    override fun onDrawerOpened(drawerView: View) {

    }
}