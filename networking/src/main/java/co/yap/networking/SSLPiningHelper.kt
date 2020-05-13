package co.yap.networking

import android.content.Context
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
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

    fun getDefaultTrustManager(): X509TrustManager {
        val trustManagers: Array<TrustManager> =
            tmf.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            ("Unexpected default trust managers:"
                    + trustManagers.contentToString())
        }
        return trustManagers[0] as X509TrustManager
    }

    private val sslContext: SSLContext = SSLContext.getInstance("TLS").apply {
        init(null, tmf.trustManagers, null)
    }

    fun getSSLFactory(): SSLSocketFactory {
        ///return sslContext.socketFactory
        return TLSSocketFactory(tmf)
    }

    class TLSSocketFactory(tmf: TrustManagerFactory) : SSLSocketFactory() {
        private val delegate: SSLSocketFactory

        init {
            val context = SSLContext.getInstance("TLS")
            context.init(null, tmf.trustManagers, null)
            delegate = context.socketFactory
        }

        override fun getDefaultCipherSuites(): Array<String> {
            return delegate.defaultCipherSuites
        }

        override fun getSupportedCipherSuites(): Array<String> {
            return delegate.supportedCipherSuites
        }

        @Throws(IOException::class)
        override fun createSocket(): Socket? {
            return enableTLSOnSocket(delegate.createSocket())
        }

        @Throws(IOException::class)
        override fun createSocket(
            s: Socket?,
            host: String?,
            port: Int,
            autoClose: Boolean
        ): Socket? {
            return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose))
        }

        @Throws(IOException::class, UnknownHostException::class)
        override fun createSocket(host: String?, port: Int): Socket? {
            return enableTLSOnSocket(delegate.createSocket(host, port))
        }

        @Throws(IOException::class, UnknownHostException::class)
        override fun createSocket(
            host: String?,
            port: Int,
            localHost: InetAddress?,
            localPort: Int
        ): Socket? {
            return enableTLSOnSocket(delegate.createSocket(host, port, localHost, localPort))
        }

        @Throws(IOException::class)
        override fun createSocket(host: InetAddress?, port: Int): Socket? {
            return enableTLSOnSocket(delegate.createSocket(host, port))
        }

        @Throws(IOException::class)
        override fun createSocket(
            address: InetAddress?,
            port: Int,
            localAddress: InetAddress?,
            localPort: Int
        ): Socket? {
            return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort))
        }

        private fun enableTLSOnSocket(socket: Socket?): Socket? {
            if (socket != null && socket is SSLSocket) {
                socket.enabledProtocols = arrayOf(
                    "TLSv1.1",
                    "TLSv1.2"
                )
            }
            return socket
        }
    }
}