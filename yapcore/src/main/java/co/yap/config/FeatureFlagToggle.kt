package co.yap.config

import android.content.Context
import co.yap.yapcore.helpers.SharedPreferenceManager

class FeatureFlagToggle {
    fun isFeatureEnable(context: Context, featureId: String, hasFeatureEnable: (Boolean) -> Unit) {
        hasFeatureEnable(
            SharedPreferenceManager.getInstance(context).getValueBoolien(
                featureId,
                false
            )
        )
    }
}