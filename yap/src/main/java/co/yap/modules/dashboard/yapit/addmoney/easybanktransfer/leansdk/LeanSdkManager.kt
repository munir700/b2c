package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk

import me.leantech.link.android.Lean

object LeanSdkManager {
    var lean: Lean? = null

    init {
        lean = Lean.Builder()
            .setAppToken("75cd536d-9c5f-44d4-9731-8d839e7d43d7")
            .setVersion("latest")
            .showLogs()
            .sandboxMode(true)
            .build()
    }
}