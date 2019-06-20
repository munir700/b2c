package co.yap.networking.interfaces

import android.app.Application

internal interface Network {
    fun initWith(application: Application, baseUrl: String)
    fun <T> createService(serviceInterface: Class<T>): T
}