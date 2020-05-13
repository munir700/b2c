package co.yap.app.activities

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import co.yap.app.R
import co.yap.app.YAPApplication
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultActivity
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.interfaces.BackPressImpl
import co.yap.yapcore.interfaces.IBaseNavigator
import java.security.MessageDigest

open class MainActivity : DefaultActivity(), IFragmentHolder, INavigator {

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private external fun bytesFromJNI(): ByteArray?

    private val actualSign = AppSignature(
        sha1 = "BE:61:41:5C:04:34:DE:D8:45:6E:C0:09:E0:95:DF:E4:49:80:A7:F6",
        md5 = "8E:9C:B9:BC:0C:EA:BF:56:81:04:CD:16:15:B9:D0:62",
        sha256 = "A5:B4:2F:1D:4A:E5:CA:CB:8D:83:5B:CE:1D:85:7A:76:41:FE:5C:B2:59:FB:3D:04:53:11:C8:BB:80:F1:66:FD"
    )

    companion object {init {
        System.loadLibrary("native-lib")
    }
    }

    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@MainActivity,
            R.id.main_nav_host_fragment
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        YAPApplication.AUTO_RESTART_APP = false
        setContentView(R.layout.activity_main)

        getApplicationSignature().find { it == actualSign }?.let {
        } ?: showToast("Signature not matched" + "^" + AlertType.DIALOG_WITH_FINISH)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment)
        if (!BackPressImpl(fragment).onBackPressed()) {
            super.onBackPressed()
        }
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun getApplicationSignature(): List<AppSignature> {
        val packageInfo = getPackageInfo()
        val signaturesList = arrayListOf<AppSignature>()
        packageInfo?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                it.signingInfo.apkContentsSigners.map { sign ->
                    signaturesList.add(
                        AppSignature(
                            sha1 = getGivenSignature("SHA", sign.toByteArray()),
                            md5 = getGivenSignature("MD5", sign.toByteArray()),
                            sha256 = getGivenSignature("SHA256", sign.toByteArray())
                        )
                    )
                }
            } else {
                if (it.signatures.isNotEmpty()) {
                    val rawCertJava = it.signatures[0].toByteArray()
                    val rawCertNative = bytesFromJNI()

                    rawCertNative?.let { byteArray ->
                        signaturesList.add(
                            AppSignature(
                                sha1 = getGivenSignature("SHA", byteArray),
                                md5 = getGivenSignature("MD5", byteArray),
                                sha256 = getGivenSignature("SHA256", byteArray)
                            )
                        )
                    }
                } else {
                }
            }
        }
        return signaturesList
    }

    data class AppSignature(var md5: String?, var sha1: String?, var sha256: String?) {

        override fun equals(other: Any?): Boolean {
            return if (other is AppSignature) {
                (this.sha1.equals(other.sha1, true)
                        && this.md5.equals(other.md5, true)
                        && this.sha256.equals(other.sha256, true))
            } else
                false
        }

        override fun hashCode(): Int {
            return super.hashCode()
        }
    }

    private fun getPackageInfo(): PackageInfo? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNING_CERTIFICATES
            )
        } else {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
        }
    }

    private fun getGivenSignature(type: String, toByteArray: ByteArray): String? {
        val digestSHA256 = MessageDigest.getInstance(type)
        digestSHA256.update(toByteArray)
        return bytesToString(digestSHA256.digest())
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = charArrayOf(
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F'
        )
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v.ushr(4)]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    private fun bytesToString(bytes: ByteArray): String? {
        val md5StrBuff = java.lang.StringBuilder()
        for (i in bytes.indices) {
            if (Integer.toHexString(0xFF and bytes[i].toInt()).length == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF and bytes[i].toInt()))
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF and bytes[i].toInt()))
            }
            if (bytes.size - 1 != i) {
                md5StrBuff.append(":")
            }
        }
        return md5StrBuff.toString()
    }

    fun onBackPressedDummy() {
        super.onBackPressed()
    }
}

