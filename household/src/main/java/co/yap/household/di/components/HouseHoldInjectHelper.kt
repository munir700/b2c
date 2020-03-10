package co.yap.household.di.components

import android.content.Context

object HouseHoldInjectHelper {
    fun provideHouseHoldComponent(applicationContext: Context): HouseHoldComponent {
        return if (applicationContext is HouseHoldComponent) {
            (applicationContext as HouseHoldComponentProvider).provideHouseHoldComponent()
        } else {
            throw IllegalStateException("The application context you have passed does not implement HouseHoldComponentProvider")
        }
    }
}