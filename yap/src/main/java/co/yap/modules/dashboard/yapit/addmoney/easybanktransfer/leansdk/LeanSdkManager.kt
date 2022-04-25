package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk

import co.yap.app.YAPApplication
import me.leantech.link.android.Lean

object LeanSdkManager {
    var lean: Lean? = null

    init {
        lean = Lean.Builder()
            .setAppToken(YAPApplication.configManager?.leanOpenBanking ?: "")
            .setVersion("latest")
            .showLogs()
            .sandboxMode(true)
            .build()
    }
}