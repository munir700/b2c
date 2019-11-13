package co.yap.modules.dashboard.yapit.topup.main.topupamount.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.ITopUpCardSuccess
import co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels.TopUpCardSuccessViewModel
import co.yap.yapcore.BaseBindingFragment

class TopUpCardSuccessFragment : BaseBindingFragment<ITopUpCardSuccess.ViewModel>(),
    ITopUpCardSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_top_card_success

    override val viewModel: ITopUpCardSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpCardSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()

    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction -> activity?.finish()
        }
    }

}