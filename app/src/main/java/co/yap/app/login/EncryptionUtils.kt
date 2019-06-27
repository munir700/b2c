package co.yap.app.login

import android.content.Context
import android.os.Build
import android.util.Log
import java.io.IOException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException

object EncryptionUtils {

    private val keyStore: KeyStore?
        get() {
            var keyStore: KeyStore? = null
            try {
                keyStore = KeyStore.getInstance(EncryptionKeyGenerator.ANDROID_KEY_STORE)
                keyStore!!.load(null)
            } catch (e: KeyStoreException) {
                Log.e("get", e.printStackTrace().toString())
            } catch (e: CertificateException) {
                Log.e("get", e.printStackTrace().toString())
            } catch (e: NoSuchAlgorithmException) {
                Log.e("get", e.printStackTrace().toString())
            } catch (e: IOException) {
                Log.e("get", e.printStackTrace().toString())
            }

            return keyStore
        }

    fun encrypt(context: Context, token: String): String? {
        val securityKey = getSecurityKey(context)
        return securityKey?.encrypt(token)
    }

    fun decrypt(context: Context, token: String): String? {
        val securityKey = getSecurityKey(context)
        return securityKey?.decrypt(token)
    }

    private fun getSecurityKey(context: Context): SecurityKey? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyStore?.let {
                EncryptionKeyGenerator.generateSecretKey(
                    it
                )
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            keyStore?.let {
                EncryptionKeyGenerator.generateKeyPairPreM(
                    context,
                    it
                )
            }
        } else {
            EncryptionKeyGenerator.generateSecretKeyPre18(context)
        }
    }

    fun clear() {
        val keyStore = keyStore
        try {
            if (keyStore!!.containsAlias(EncryptionKeyGenerator.KEY_ALIAS)) {
                keyStore.deleteEntry(EncryptionKeyGenerator.KEY_ALIAS)
            }
        } catch (e: KeyStoreException) {
            Log.e("get", e.printStackTrace().toString())
        }

    }
}