package co.yap.modules.dashboard.yapit.addmoney.landing

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddMoneyLandingBinding
import co.yap.modules.dashboard.more.cdm.CdmMapFragment
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyBaseFragment
import co.yap.translation.Strings
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.interfaces.OnItemClickListener

class AddMoneyLandingFragment : AddMoneyBaseFragment<IAddMoneyLanding.ViewModel>(),
    IAddMoneyLanding.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_money_landing

    override val viewModel: IAddMoneyLanding.ViewModel
        get() = ViewModelProviders.of(this).get(AddMoneyLandingViewModel::class.java)

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

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
    }

    private fun setupRecycleView() {
        getBinding().recyclerOptions.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(R.dimen.margin_normal_large) ?: 16, 2, true
            )
        )
        viewModel.landingAdapter.allowFullItemClickListener = true
        viewModel.landingAdapter.setItemListener(listener)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AddMoneyLandingOptions)
                viewModel.clickEvent.setValue(data.id)
        }
    }


    private val observer = Observer<Int> {
        when (it) {
            Constants.ADD_MONEY_TOP_UP_VIA_CARD -> {
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_top_via_card))
            }
            Constants.ADD_MONEY_SAMSUNG_PAY -> {
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_samsung_pay))
            }
            Constants.ADD_MONEY_GOOGLE_PAY -> {
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_google_pay))
            }
            Constants.ADD_MONEY_BANK_TRANSFER -> {
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_bank_transfer))
            }
            Constants.ADD_MONEY_CASH_OR_CHEQUE -> {
                startFragment(CdmMapFragment::class.java.name)
            }
            Constants.ADD_MONEY_QR_CODE -> {
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_qr_code))
            }
        }
    }

    override fun getBinding(): FragmentAddMoneyLandingBinding {
        return viewDataBinding as FragmentAddMoneyLandingBinding
    }

}