package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IEidInfoReview
import co.yap.modules.onboarding.viewmodels.EidInfoReviewViewModel
import co.yap.yapcore.BaseBindingActivity

class EidInfoReviewActivity : BaseBindingActivity<IEidInfoReview.ViewModel>() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, EidInfoReviewActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_eid_info_review

    override val viewModel: IEidInfoReview.ViewModel
        get() = ViewModelProviders.of(this).get(EidInfoReviewViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}