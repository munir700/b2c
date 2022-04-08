package co.yap.modules.dashboard.yapit.addmoney.easybanktransfer.leansdk

import me.leantech.link.android.Lean

object LeanSdkManager {
    var lean: Lean? = null

    init {
        lean = Lean.Builder()
            .setAppToken("9f9b7a4a-e470-4aba-b175-0987309e93db")
            .setVersion("latest")
            .showLogs()
            .sandboxMode(true)
            .build()
    }
}