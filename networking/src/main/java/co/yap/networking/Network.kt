package co.yap.networking

internal interface Network {
    fun <T> createService(serviceInterface :Class<T>): T
}