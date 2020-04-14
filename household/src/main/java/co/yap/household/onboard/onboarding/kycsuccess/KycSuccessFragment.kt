package co.yap.household.onboard.onboarding.kycsuccess

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentKycSuccessBinding
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.trackAdjustEvent
import kotlinx.android.synthetic.main.activity_kyc_success.*
import javax.inject.Inject

class KycSuccessFragment :
    BaseNavViewModelFragment<FragmentKycSuccessBinding, IKycSuccess.State, KycSuccessVM>() {
    override fun getBindingVariable() = BR.kycSuccessVM

    override fun getLayoutId() = R.layout.fragment_kyc_success
    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        btnTopUp.setOnClickListener {
            trackAdjustEvent(AdjustEvents.KYC_END.type)
            launchActivity<LiteDashboardActivity>(clearPrevious = true)
        }
    }
}