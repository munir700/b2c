package co.yap.modules.di.components

import android.content.Context

object YapInjectHelper {
    fun provideYapComponent(applicationContext: Context): YapComponent {
        return if (applicationContext is YapComponent) {
            (applicationContext as YapComponentProvider).provideYapComponent()
        } else {
            throw IllegalStateException("The application context you have passed does not implement YapComponentProvider")
        }
    }
}