package co.yap.yapcore.managers

import co.yap.yapcore.enums.UserAccessRestriction

object FeatureProvisioning {
    private var blockedFeatures: ArrayList<String> = arrayListOf()
    var restrictions: ArrayList<UserAccessRestriction> = arrayListOf()
    fun configure(blockedFeatures: ArrayList<String>, restrictions: ArrayList<UserAccessRestriction>) {
        this.blockedFeatures = blockedFeatures
        this.restrictions = restrictions
    }

    fun getFeatureProvisioning(screenName: String): Boolean {
        return blockedFeatures.contains(screenName)
    }

    fun getBlockedBy() {

    }

}