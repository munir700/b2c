package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.modules.kyc.viewmodels.ConfirmCardNameViewModel
import co.yap.yapcore.BaseBindingFragment

class ConfirmCardNameFragment : BaseBindingFragment<IConfirmCardName.ViewModel>(),
    IConfirmCardName.View {
    override val viewModel: IConfirmCardName.ViewModel
        get() = ViewModelProvider(this).get(ConfirmCardNameViewModel::class.java)

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_onboarding_confirm_card_name
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnNameFine -> {
                    //handle click event for name is fine scenario
                }
                R.id.tvEditCardName -> {
                    //handle click event for editing name
                }
            }
        })
    }
}