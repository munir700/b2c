package co.yap.modules.dashboard.addionalinfo.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAdditionalInfoStartBinding
import co.yap.modules.dashboard.addionalinfo.activities.AdditionalInfoActivity
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoStart
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoStartViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.extentions.launchActivity

class AdditionalInfoStartFragment : BaseBindingFragment<IAdditionalInfoStart.ViewModel>(),
    IAdditionalInfoStart.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_start

    override val viewModel: IAdditionalInfoStart.ViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoStartViewModel::class.java)

    private fun getBindings(): FragmentAdditionalInfoStartBinding =
        viewDataBinding as FragmentAdditionalInfoStartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.subTitle.set("We need additional information to be able to proceed with your application to get your YAP account activated")
        viewModel.state.title.set("Hey Nada,")
    }

    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                launchActivity<AdditionalInfoActivity> { }
            }
        }
    }
}