package co.yap.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetroNetwork : Network {
    private const val READ_TIMEOUT_SECONDS = 60L
    private const val CONNECTION_TIMEOUT_SECONDS = 60L
    private var retro: Retrofit? = null

    override fun <T> createService(serviceInterface: Class<T>): T = build("").create(serviceInterface)

    private fun build(baseUrl: String): Retrofit {
        if (retro == null) {
            val client = buildClient()
            retro = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()
        }

        return retro!!

    }

    private fun buildClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        // TODO: Add network and auth interceptors
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(logger)
//            .addInterceptor(AddCookiesInterceptor())
//            .addInterceptor(ReceivedCookiesInterceptor())
//            .addInterceptor(AuthorizationInterceptor(getSession()))
            .build()
    }
}