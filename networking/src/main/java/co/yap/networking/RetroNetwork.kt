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
//   added multiple key using this as directed in this link https://stackoverflow.com/questions/24006545/how-can-i-pin-a-certificate-with-square-okhttp to fix
//   <-- HTTP FAILED: javax.net.ssl.SSLPeerUnverifiedException: Certificate pinning failure!
        val certPinner = CertificatePinner.Builder()
            /*.add(
                "*.yap.co", "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA=",
                "sha256/ZrRL6wSXl/4lm1KItkcZyh56BGOoxMWUDJr7YVqE4no=",
                "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8=",
                "sha256/jr1RBEN+F3KtPTYBMhudiTGBRAg8k2qZPEg3WbSerXU="*/
            .add("*.yap.co", "sha256/e5L5CAoQjV0HFzAnunk1mPHVx1HvPxcfJYI0UtLyBwY=")
             .add("*.yap.co", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA")
            .add("*.yap.co", "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add("*.yap.co", "sha256/jr1RBEN+F3KtPTYBMhudiTGBRAg8k2qZPEg3WbSerXU=")
            .build()

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