package co.yap.modules.dashboard.addionalinfo.fragments

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAdditionalInfoCompleteBinding
import co.yap.modules.dashboard.addionalinfo.interfaces.IAdditionalInfoComplete
import co.yap.modules.dashboard.addionalinfo.viewmodels.AdditionalInfoCompleteViewModel
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.managers.SessionManager

class AdditionalInfoCompleteFragment : BaseBindingFragment<IAdditionalInfoComplete.ViewModel>(),
    IAdditionalInfoComplete.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_additional_info_complete

    override val viewModel: IAdditionalInfoComplete.ViewModel
        get() = ViewModelProviders.of(this).get(AdditionalInfoCompleteViewModel::class.java)

    private fun getBindings(): FragmentAdditionalInfoCompleteBinding =
        viewDataBinding as FragmentAdditionalInfoCompleteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.subTitle.set("Your application is back in process and we will inform you soon of when you can set your card pin")
        viewModel.state.title.set("Thanks " + SessionManager.user?.currentCustomer?.firstName)
    }


    override fun onToolBarClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                setResultFinish()
            }
        }
    }

    private fun setResultFinish() {
        requireActivity().setResult(Activity.RESULT_OK)
        requireActivity().finish()
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}