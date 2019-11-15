package co.yap.modules.dashboard.yapit.topup.main.topupamount.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopUpCardSuccessBinding
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.ITopUpCardSuccess
import co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels.TopUpCardSuccessViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.Utils

class TopUpCardSuccessFragment : BaseBindingFragment<ITopUpCardSuccess.ViewModel>(),
    ITopUpCardSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_top_up_card_success

    override val viewModel: ITopUpCardSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpCardSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)
    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction -> activity?.finish()
        }
    }

    private fun setUpData() {

        viewModel.state.topUpSuccess =
            getString(Strings.screen_topup_success_display_text_success_transaction_message).format(
                viewModel.state.currencyType,
                Utils.getFormattedCurrency(viewModel.state.amount)
            )

        getBindings().tvTopUp.text = Utils.getSppnableStringForAmount(
            requireContext(),
            viewModel.state.topUpSuccess,
            viewModel.state.currencyType,
            viewModel.state.amount
        )
    }

    private fun getBindings(): FragmentTopUpCardSuccessBinding {
        return viewDataBinding as FragmentTopUpCardSuccessBinding

    }

}