package co.yap.household.onboard.onboarding.kycsuccess

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentKycSuccessBinding
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.activity_kyc_success.*

class KycSuccessFragment :
    BaseNavViewModelFragment<FragmentKycSuccessBinding, IKycSuccess.State, KycSuccessVM>() {
    override fun getBindingVariable() = BR.kycSuccessVM

    override fun getLayoutId() = R.layout.fragment_kyc_success

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        if (MyUserManager.isExistingUser()) {
            trackAdjustPlatformEvent(AdjustEvents.HH_USER_EXISTING_ACCOUNT_ACTIVE.type)
        } else {
            trackAdjustPlatformEvent(AdjustEvents.ONBOARD_NEW_HH_USER_ONBOARDING_SUCCESS.type)
        }
        btnTopUp.setOnClickListener {
            trackAdjustPlatformEvent(AdjustEvents.KYC_END.type)
            launchActivity<LiteDashboardActivity>(clearPrevious = true)
        }
    }
}
