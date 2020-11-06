package co.yap.modules.dashboard.yapit.sendmoney.landing

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentSendMoneyLandingBinding
import co.yap.modules.dashboard.yapit.sendmoney.landing.viewmodels.SendMoneyLandingViewModel
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyBaseFragment
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.interfaces.OnItemClickListener


class SendMoneyLandingFragment : SendMoneyBaseFragment<ISendMoneyLanding.ViewModel>(),
    ISendMoneyLanding.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_send_money_landing
    override val viewModel: SendMoneyLandingViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyLandingViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
    }
    override fun setObservers() {
        viewModel.clickEvent.observe(this, observer)
    }

    private fun setupRecycleView() {
        getBinding().recyclerOptions.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(R.dimen.margin_normal_large) ?: 16, 2, true
            )
        )
        viewModel.landingAdapter.allowFullItemClickListener = true
        viewModel.landingAdapter.setItemListener(itemClickListener)
    }

    private val itemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            viewModel.clickEvent.setValue(pos)
        }
    }

    private val observer = Observer<Int> {
        when (it) {
            SEND_MONEY_TO_YAP_CONTACTS -> {
            }
            SEND_MONEY_TO_LOCALE_BANK -> {
            }
            SEND_MONEY_TO_INTERNATIONAL -> {
            }
            SEND_MONEY_TO_HOME_COUNTRY -> {
            }
            SEND_MONEY_QR_CODE -> {

            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    override fun getBinding(): FragmentSendMoneyLandingBinding {
        return viewDataBinding as FragmentSendMoneyLandingBinding
    }
}
