package co.yap.networking

import android.content.Context
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

class SSLPiningHelper(val context: Context) {

    private val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
    private val caInput: InputStream = BufferedInputStream(getInputStream("yap-cert.crt"))

    private fun getInputStream(name: String): InputStream? {
        return try {
            val inputStream = context.assets.open(name)
            BufferedInputStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private val ca: X509Certificate = caInput.use {
        cf.generateCertificate(it) as X509Certificate
    }
    private val keyStoreType = KeyStore.getDefaultType()
    private val keyStore = KeyStore.getInstance(keyStoreType).apply {
        load(null, null)
        setCertificateEntry("ca", ca)
    }
    private val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
    private val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm).apply {
        init(keyStore)
    }

    val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
        init(null, tmf.trustManagers, null)
    }

    fun getDefaultTrustManager(): X509TrustManager {
        val trustManagers: Array<TrustManager> =
            tmf.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            ("Unexpected default trust managers:"
                    + trustManagers.contentToString())
        }
        return trustManagers[0] as X509TrustManager
    }

    fun getSSLFactory(): SSLSocketFactory {
        return sslContext.socketFactory
    }
}