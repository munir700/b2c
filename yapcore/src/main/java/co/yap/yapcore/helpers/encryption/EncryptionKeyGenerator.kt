package co.yap.yapcore.helpers.encryption

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.io.FileNotFoundException
import java.io.IOException
import java.math.BigInteger
import java.security.*
import java.security.cert.CertificateException
import java.util.*
import javax.crypto.KeyGenerator
import javax.security.auth.x500.X500Principal

object EncryptionKeyGenerator {
    const val ANDROID_KEY_STORE = "AndroidKeyStore"
    const val KEY_ALIAS = "KEY_ALIAS"
    private const val KEY_STORE_FILE_NAME = "KEY_STORE"
    private const val KEY_STORE_PASSWORD = "KEY_STORE_PASSWORD"

    @TargetApi(Build.VERSION_CODES.M)
    internal fun generateSecretKey(keyStore: KeyStore): SecurityKey? {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    ANDROID_KEY_STORE
                )
                keyGenerator.init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    ).setBlockModes(
                        KeyProperties.BLOCK_MODE_GCM
                    )
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setRandomizedEncryptionRequired(false)
                        .build()
                )
                return SecurityKey(keyGenerator.generateKey())
            }
        } catch (e: KeyStoreException) {
        } catch (e: NoSuchProviderException) {
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: InvalidAlgorithmParameterException) {
        }

        try {
            val entry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.SecretKeyEntry
            return SecurityKey(entry.secretKey)
        } catch (e: KeyStoreException) {
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: UnrecoverableEntryException) {
        }

        return null
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    internal fun generateKeyPairPreM(context: Context, keyStore: KeyStore): SecurityKey? {
        try {
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                val start = Calendar.getInstance()
                val end = Calendar.getInstance()
                //1 Year validity
                end.add(Calendar.YEAR, 1)

                val spec = KeyPairGeneratorSpec.Builder(context).setAlias(KEY_ALIAS)
                    .setSubject(X500Principal("CN=$KEY_ALIAS"))
                    .setSerialNumber(BigInteger.TEN)
                    .setStartDate(start.time)
                    .setEndDate(end.time)
                    .build()

                val kpg = KeyPairGenerator.getInstance(
                    "RSA",
                    ANDROID_KEY_STORE
                )
                kpg.initialize(spec)
                kpg.generateKeyPair()
            }
        } catch (e: KeyStoreException) {
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: InvalidAlgorithmParameterException) {
        } catch (e: NoSuchProviderException) {
        }

        try {
            val entry = keyStore.getEntry(KEY_ALIAS, null) as KeyStore.PrivateKeyEntry
            return SecurityKey(
                KeyPair(entry.certificate.publicKey, entry.privateKey)
            )
        } catch (e: KeyStoreException) {
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: UnrecoverableEntryException) {
        }

        return null
    }

    internal fun generateSecretKeyPre18(context: Context): SecurityKey? {

        try {
            val androidCAStore = KeyStore.getInstance(KeyStore.getDefaultType())

            val password = KEY_STORE_PASSWORD.toCharArray()

            val isKeyStoreLoaded =
                loadKeyStore(context, androidCAStore, password)
            val protParam = KeyStore.PasswordProtection(password)
            if (!isKeyStoreLoaded || !androidCAStore.containsAlias(KEY_ALIAS)) {
                //Create and save new secret key
                saveMyKeystore(
                    context,
                    androidCAStore,
                    password,
                    protParam
                )
            }

            // Fetch Secret Key
            val pkEntry = androidCAStore.getEntry(KEY_ALIAS, protParam) as KeyStore.SecretKeyEntry

            return SecurityKey(pkEntry.secretKey)
        } catch (e: KeyStoreException) {
        } catch (e: IOException) {

        } catch (e: CertificateException) {

        } catch (e: NoSuchAlgorithmException) {

        } catch (e: UnrecoverableEntryException) {

        }

        return null
    }

    private fun loadKeyStore(
        context: Context,
        androidCAStore: KeyStore,
        password: CharArray
    ): Boolean {
        val fis: java.io.FileInputStream
        try {
            fis = context.openFileInput(KEY_STORE_FILE_NAME)
        } catch (e: FileNotFoundException) {

            return false
        }

        try {
            androidCAStore.load(fis, password)
            return true
        } catch (e: IOException) {
        } catch (e: NoSuchAlgorithmException) {
        } catch (e: CertificateException) {
        }

        return false
    }

    @Throws(
        NoSuchAlgorithmException::class,
        KeyStoreException::class,
        IOException::class,
        CertificateException::class
    )
    private fun saveMyKeystore(
        context: Context, androidCAStore: KeyStore, password: CharArray,
        protParam: KeyStore.ProtectionParameter
    ) {

        val mySecretKey = KeyGenerator.getInstance("AES").generateKey()

        val skEntry = KeyStore.SecretKeyEntry(mySecretKey)
        androidCAStore.load(null)
        androidCAStore.setEntry(KEY_ALIAS, skEntry, protParam)
        var fos: java.io.FileOutputStream? = null
        try {
            fos = context.openFileOutput(KEY_STORE_FILE_NAME, Context.MODE_PRIVATE)

            androidCAStore.store(fos, password)
        } finally {
            fos?.close()
        }
    }
}