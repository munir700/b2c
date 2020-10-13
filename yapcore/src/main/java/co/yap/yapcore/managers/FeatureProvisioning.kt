package co.yap.yapcore.managers

import co.yap.yapcore.enums.FeatureSet
import co.yap.yapcore.enums.UserAccessRestriction

object FeatureProvisioning {
    private var blockedFeatures: ArrayList<FeatureSet> = arrayListOf()
    private var restrictions: ArrayList<UserAccessRestriction> = arrayListOf()
    fun configure(
        blockedFeatures: ArrayList<FeatureSet>,
        restrictions: ArrayList<UserAccessRestriction>
    ) {
        this.blockedFeatures = blockedFeatures
        this.restrictions = restrictions
    }

    fun getFeatureProvisioning(screenType: FeatureSet): Boolean {
        return blockedFeatures.contains(screenType)
    }

    fun getUserAccessRestriction(): UserAccessRestriction {
        return restrictions.firstOrNull() ?: UserAccessRestriction.NONE
    }
}