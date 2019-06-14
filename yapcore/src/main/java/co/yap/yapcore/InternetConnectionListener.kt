package co.yap.yapcore;

interface InternetConnectionListener {
    fun onConnectivityChange(isAvailable: Boolean)
}