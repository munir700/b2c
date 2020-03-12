package co.yap.household.onboard.onboarding.kycsuccess

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentKycSuccessBinding
import co.yap.yapcore.dagger.base.BaseViewModelActivity
import co.yap.yapcore.dagger.base.BaseViewModelFragment
import javax.inject.Inject

class KycSuccessFragment :
    BaseViewModelFragment<FragmentKycSuccessBinding, IKycSuccess.State, KycSuccessVM>() {
    override fun getBindingVariable() = BR.kycSuccessVM

    override fun getLayoutId() = R.layout.fragment_kyc_success
}