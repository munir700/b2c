package co.yap.yapcore.flagsmith

import co.yap.yapcore.BuildConfig


val getFeatureFlagClient: FSClient
    get() {
        return if (BuildConfig.DEBUG) {
            FSClient.get() //MockFeatureClient()
        } else {
            FSClient.get()
        }
    }

