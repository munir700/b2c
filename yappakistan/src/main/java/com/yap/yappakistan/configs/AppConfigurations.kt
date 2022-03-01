package com.yap.yappakistan.configs

import com.yap.yappakistan.networking.apiclient.base.RetroNetwork

object AppConfigurations {

    private var appConfigs: BuildConfigurations? = null
    private var retrofitNetwork: RetroNetwork? = null

    fun init(configs: BuildConfigurations?, retroNetwork: RetroNetwork) {
        appConfigs = configs
        retrofitNetwork = retroNetwork
    }

    fun getAppConfigs(): BuildConfigurations? {
        return appConfigs
    }

    fun getRetrofit(): RetroNetwork? {
        return retrofitNetwork
    }
}
