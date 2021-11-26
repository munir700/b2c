package co.yap.yapcore.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import androidx.databinding.ObservableBoolean
import java.util.*
import kotlin.collections.ArrayList

object NetworkConnectionManager {
    interface OnNetworkStateChangeListener {
        fun onNetworkStateChanged(isConnected: Boolean)
    }

    private var listeners: ArrayList<OnNetworkStateChangeListener> = arrayListOf()
    private var isRegistered: Boolean = false

    fun init(context: Context) {
        if (!isRegistered) {
            isRegistered = true
            context.applicationContext.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    fun destroy(context: Context) {
        unsubscribeAll()
        if (isRegistered) {
            // TODO: Crash - java.lang.IllegalArgumentException:
            context.applicationContext.unregisterReceiver(receiver)
            isRegistered = false
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            handleConnectivityChange(context)
        }
    }

    private fun handleConnectivityChange(context: Context) {
        val isConnected = isNetworkAvailable(context)
        for (listener in listeners) {
            listener.onNetworkStateChanged(isConnected)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
        return capabilities?.hasCapability(NET_CAPABILITY_INTERNET) == true
    }

    fun subscribe(listener: OnNetworkStateChangeListener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener)
        }
    }

    private fun unsubscribeAll() {
        listeners.clear()
    }

    fun unsubscribe(listener: OnNetworkStateChangeListener) {
        listeners.remove(listener)
    }
}