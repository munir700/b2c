package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk

import co.yap.app.YAPApplication
import co.yap.yapcore.enums.ProductFlavour
import me.leantech.link.android.Lean

object LeanSdkManager {
    var lean: Lean? = null

    init {
        lean = Lean.Builder()
            .setAppToken(YAPApplication.configManager?.leanOpenBanking ?: "")
            .setVersion("latest")
            .showLogs()
            .sandboxMode(!(YAPApplication.configManager?.flavor.equals(ProductFlavour.PROD.flavour) || YAPApplication.configManager?.flavor.equals(ProductFlavour.PREPROD.flavour)))
            .build()
    }
}