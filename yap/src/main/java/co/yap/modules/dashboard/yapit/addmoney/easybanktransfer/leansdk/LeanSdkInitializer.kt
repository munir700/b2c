package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import co.yap.app.YAPApplication
import co.yap.yapcore.enums.ProductFlavour
import me.leantech.link.android.Lean

class LeanSdkInitializer : LifecycleEventObserver {

    private var lean: Lean? = null

    fun getLeanInstance() = lean

    private fun initializeSdk() {
        if (lean == null) {
            lean = Lean.Builder()
                .setAppToken(YAPApplication.configManager?.leanOpenBanking ?: "")
                .setVersion("latest")
                .showLogs()
                .sandboxMode(getSandBoxValue())
                .build()
        }
    }

    private fun removeSdk() {
        lean = null
    }

    /**
     * This method is called whenever there is changed in lifecycle
     */
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                initializeSdk()
            }
            Lifecycle.Event.ON_DESTROY -> {
                removeSdk()
            }
            else -> {}
        }
    }

    private fun getSandBoxValue() =
        !(YAPApplication.configManager?.flavor.equals(ProductFlavour.PROD.flavour))
}