package co.yap.modules.kyc.fragments

import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.viewmodels.EidInfoReviewViewModel
import co.yap.modules.onboarding.interfaces.IEidInfoReview

class EidInfoReviewFragment : KYCChildFragment<IEidInfoReview.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_eid_info_review

    override val viewModel: IEidInfoReview.ViewModel
        get() = ViewModelProviders.of(this).get(EidInfoReviewViewModel::class.java)


}