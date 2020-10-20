package co.yap.modules.dashboard.yapit.addmoney

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddMoneyBinding
import co.yap.translation.Strings
import co.yap.widgets.SpaceGridItemDecoration
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.interfaces.OnItemClickListener

class AddMoneyFragment : BaseBindingFragment<IAddMoney.ViewModel>(), IAddMoney.View {
    lateinit var adapter: AddMoneyAdapter
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_money

    override val viewModel: IAddMoney.ViewModel
        get() = ViewModelProviders.of(this).get(AddMoneyViewModel::class.java)

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
        adapter = AddMoneyAdapter(requireContext(), viewModel.getAddMoneyOptions())
        getBinding().recyclerOptions.adapter = adapter

        getBinding().recyclerOptions.addItemDecoration(
            SpaceGridItemDecoration(
                dimen(R.dimen.margin_normal_large) ?: 16, 2, true
            )
        )
        adapter.allowFullItemClickListener = true
        adapter.setItemListener(listener)
    }

    private val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is AddMoneyOptions)
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
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_cash_or_cheque))
            }
            Constants.ADD_MONEY_QR_CODE -> {
                showToast(getString(Strings.screen_fragment_yap_it_add_money_text_qr_code))
            }
        }
    }

    override fun getBinding(): FragmentAddMoneyBinding {
        return viewDataBinding as FragmentAddMoneyBinding
    }

}