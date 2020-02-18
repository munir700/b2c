package co.yap.networking

import android.app.Application
import android.content.Context
import android.os.Environment
import co.yap.networking.intercepters.CookiesInterceptor
import co.yap.networking.intercepters.NetworkConstraintsInterceptor
import co.yap.networking.intercepters.SessionValidator
import co.yap.networking.interfaces.Network
import co.yap.networking.interfaces.NetworkConstraintsListener
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetroNetwork : Network {
    private const val READ_TIMEOUT_SECONDS = 60L
    private const val CONNECTION_TIMEOUT_SECONDS = 60L
    private const val DISK_CACHE_SIZE = (10 * 1024 * 1024).toLong() // 10 MB

    private var retro: Retrofit? = null
    private var networkConstraintsListener: NetworkConstraintsListener? = null
        get() {
            if (field == null) field = NetworkConstraintsListener.DEFAULT
            return field
        }

    override fun initWith(application: Application, baseUrl: String) {
        build(application, baseUrl)
    }

    @Throws(IllegalStateException::class)
    override fun <T> createService(serviceInterface: Class<T>): T {
        if (retro == null) throw IllegalStateException("RetroNetwork is not initialised. Make sure you have called 'initWith' before calling this function ")
        return retro?.create(serviceInterface)!!
    }

    private fun build(context: Context, baseUrl: String): Retrofit {
        if (retro == null) {
            retro = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttpClient(context)).build()
        }
        return retro!!
    }

    private fun buildOkHttpClient(context: Context): OkHttpClient {
        //add ssl pinning certificate code start

        //"yap.co should be replace with base url
        val certPinner = CertificatePinner.Builder()
            .add(
                 "*.yap.co",
                "sha256/jr1RBEN+F3KtPTYBMhudiTGBRAg8k2qZPEg3WbSerXU="
//                "sha256/OEVCRDUxMDQ0MzdFMTc3MkFEM0QzNjAxMzIxQjlEODkzMTgxNDQwODNDOTM2QTk5M0M0ODM3NTlCNDlFQUQ3NQ=="
//                "sha256/QzggOTcgMzIgRDggNUEgRTIgOTggMzcgMDAgOEQgMEEgRUYgMEMgMzIgMjYgOTggNTIgNjcgNkIgODMgQTMgOTQgRjQgMzggMkMgMDcgOUUgRjUgNTggRjYgMEIgQkU="
            )
            .build()// as per old code and article

        //add ssl pinning certificate code end

        val logger = HttpLoggingInterceptor()
        logger.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(getCache())
            .certificatePinner(certPinner)      //add ssl pinning certificate code start
            .addInterceptor(logger)
            .addInterceptor(CookiesInterceptor())
            .addInterceptor(object : NetworkConstraintsInterceptor(context) {
                override fun onInternetUnavailable() {
                    networkConstraintsListener?.onInternetUnavailable()
                }

                override fun onCacheUnavailable() {
                    networkConstraintsListener?.onCacheUnavailable()
                }
            })
            .addInterceptor(object : SessionValidator() {
                override fun invalidate() {
                    networkConstraintsListener?.onSessionInvalid()
                }
            })
            .build()
    }

    fun listenNetworkConstraints(listener: NetworkConstraintsListener) {
        this.networkConstraintsListener = listener
    }

    private fun getCache(): Cache {
        val cacheDir = File(Environment.getDataDirectory(), "cache")
        return Cache(cacheDir, DISK_CACHE_SIZE)
    }

}