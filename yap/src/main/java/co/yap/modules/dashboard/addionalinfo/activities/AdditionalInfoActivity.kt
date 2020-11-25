package co.yap.modules.dashboard.addionalinfo.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.ActivityAdditionalInfoBinding
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfo
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.IFragmentHolder
import co.yap.yapcore.defaults.DefaultNavigator
import co.yap.yapcore.defaults.INavigator
import co.yap.yapcore.interfaces.IBaseNavigator
import kotlinx.android.synthetic.main.activity_additional_info.*

class AdditionalInfoActivity : BaseBindingActivity<IAdditionalInfo.ViewModel>(),
    IFragmentHolder, INavigator, IAdditionalInfo.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_additional_info


    override val viewModel: IAdditionalInfo.ViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoViewModel::class.java)
    override val navigator: IBaseNavigator
        get() = DefaultNavigator(
            this@AdditionalInfoActivity,
            R.id.additional_info_host_fragment
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObserver()
    }

    private fun getBindings(): ActivityAdditionalInfoBinding =
        viewDataBinding as ActivityAdditionalInfoBinding

    override fun setObserver() {
        viewModel.stepCount.observe(this, Observer {
            if (it < getBindings().stepView.stepCount) {
                step_view.go(it, true)
            } else {
                step_view.done(true)
            }
        })
    }

    override fun onBackPressed() {
//        val step = viewModel.stepCount.value ?: 0
//        if (step > 0) {
//            viewModel.stepCount.value = step - 1
//        }
//        super.onBackPressed()
    }
}