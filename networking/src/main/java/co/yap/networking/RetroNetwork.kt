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

    override fun initWith(application: Application, appData: AppData) {
        build(application, appData)
    }

    @Throws(IllegalStateException::class)
    override fun <T> createService(serviceInterface: Class<T>): T {
        if (retro == null) throw IllegalStateException("RetroNetwork is not initialised. Make sure you have called 'initWith' before calling this function ")
        return retro?.create(serviceInterface)!!
    }

    private fun build(context: Context, appData: AppData): Retrofit {
        if (retro == null) {
            retro = Retrofit.Builder()
                .baseUrl(appData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttpClient(context, appData)).build()
        }
        return retro!!
    }

    private fun buildOkHttpClient(context: Context, appData: AppData): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .cache(getCache())
            .addInterceptor(getHttpLoggingInterceptor(appData))
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
        return okHttpClientBuilder.build()
        //return sslImplementation(context, okHttpClientBuilder, appData)
    }

    private fun sslImplementation(
        context: Context,
        builder: OkHttpClient.Builder,
        appData: AppData
    ): OkHttpClient {
        return if (appData.flavor.equals("stg", true) && appData.build_type.equals(
                "release",
                true
            )
        ) {
            builder.certificatePinner(getCertificatePinner())
            builder.sslSocketFactory(
                SSLPiningHelper(context).getSSLFactory(),
                SSLPiningHelper(context).getDefaultTrustManager()
            ).build()
        } else {
            builder.build()
        }
    }

    private fun getCertificatePinner(): CertificatePinner {
        return CertificatePinner.Builder()
            .add("*.yap.co", "sha256/e5L5CAoQjV0HFzAnunk1mPHVx1HvPxcfJYI0UtLyBwY=")
            .add("*.yap.co", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add("*.yap.co", "sha256/xYUxUshCD5PVwQ1AgAakwEG6dLIId5QMvqbNVBn1vFw=") // charles
            .add("*.yap.co", "sha256/Yf/ZlETuML9yDZbbwEFNdRnXKM/Nci/pXaCLCcH8yrU=") // charles
            .add("*.yap.co", "sha256/jr1RBEN+F3KtPTYBMhudiTGBRAg8k2qZPEg3WbSerXU=")
            .add("*.yap.co", "sha256/yJcy2FrimDcAjQrvDDImmFJna4OjlPQ4LAee9Vj2C74=")
             //stage server
            .add("*.yap.co", "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=")
            .add("*.yap.co", "sha256/ZrRL6wSXl/4lm1KItkcZyh56BGOoxMWUDJr7YVqE4no=")
            .add("*.yap.co", "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=")
            .add("*.yap.co", "sha256/VjLZe/p3W/PJnd6lL8JVNBCGQBZynFLdZSTIqcO0SJ8=")
            .build()


    }

    private fun getHttpLoggingInterceptor(
        appData: AppData
    ): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.level =
            if (appData.flavor.equals("stg", true)
                && appData.build_type.equals("release", true)
            ) {
                HttpLoggingInterceptor.Level.NONE
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
        return logger
    }

    fun listenNetworkConstraints(listener: NetworkConstraintsListener) {
        this.networkConstraintsListener = listener
    }

    private fun getCache(): Cache {
        val cacheDir = File(Environment.getDataDirectory(), "cache")
        return Cache(cacheDir, DISK_CACHE_SIZE)
    }

}