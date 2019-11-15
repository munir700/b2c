package co.yap.modules.dashboard.yapit.topup.main.topupamount.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.IVerifyCardCvv
import co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels.VerifyCardCvvViewModel
import co.yap.yapcore.BaseBindingFragment

class VerifyCardCvvFragment : BaseBindingFragment<IVerifyCardCvv.ViewModel>(), IVerifyCardCvv.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_verify_card_cvv

    override val viewModel: IVerifyCardCvv.ViewModel
        get() = ViewModelProviders.of(this).get(VerifyCardCvvViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.clickEvent.observe(this, clickEvent)
    }

    var clickEvent = Observer<Int> {

        when (it) {
            R.id.btnAction ->
                findNavController().navigate(R.id.action_verifyCardCvvFragment_to_topUpCardSuccessFragment)
        }
    }

}