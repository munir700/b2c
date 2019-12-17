package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.R
import co.yap.databinding.FragmentCashTransferBinding
import co.yap.modules.dashboard.yapit.sendmoney.activities.BeneficiaryCashTransferActivity
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.ICashTransfer
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.viewmodels.CashTransferViewModel
import co.yap.modules.dashboard.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.translation.Strings
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_y2y_funds_transfer.*

class CashTransferFragment : SendMoneyBaseFragment<ICashTransfer.ViewModel>(), ICashTransfer.View {

//    val args: Y2YTransferFragmentArgs by navArgs()

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_cash_transfer

    override val viewModel: CashTransferViewModel
        get() = ViewModelProviders.of(this).get(CashTransferViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.availableBalance = MyUserManager.cardBalance.value?.availableBalance
        setObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpData()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickEvent)

        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })


    }

    val clickEvent = Observer<Int> {
        when (it) {
            R.id.btnConfirm -> {
                val action =
                    CashTransferFragmentDirections.actionCashTransferFragmentToGenericOtpLogoFragment(
                        false,
                        Constants.BENEFICIARY_CASH_TRANSFER,
                        viewModel.state.amount

                    )
                findNavController().navigate(action)
            }
            Constants.ADD_CASH_PICK_UP_SUCCESS -> {
                val action =
                    CashTransferFragmentDirections.actionCashTransferFragmentToTransferSuccessFragment2(
                        "",
                        viewModel.state.currencyType,
                        Utils.getFormattedCurrency(viewModel.state.amount)
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun setUpData() {
        if (context is BeneficiaryCashTransferActivity) {
            (context as BeneficiaryCashTransferActivity).viewModel.state.otpSuccess?.let {
                if (it) {
                    (context as BeneficiaryCashTransferActivity).viewModel.state.beneficiary?.id?.let { beneficiaryId ->
                        viewModel.cashPayoutTransferRequest(beneficiaryId.toString())
                    }

                }
            }
        }

        if (activity is BeneficiaryCashTransferActivity) {
            (activity as BeneficiaryCashTransferActivity).let { it ->
                it.viewModel.state.leftButtonVisibility = false
                it.viewModel.state.beneficiary?.let {
                    viewModel.state.fullName = "${it.firstName} ${it.lastName}"
                }
            }
        }

        viewModel.state.availableBalanceText =
            " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                viewModel.state.availableBalance
            )

        etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        etAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 0) {
                    etAmount.gravity = Gravity.CENTER
                } else {
                    etAmount.gravity = Gravity.START or Gravity.CENTER_VERTICAL
                }
            }
        })
    }

    private fun showErrorSnackBar() {
        CustomSnackbar.showErrorCustomSnackbar(
            context = requireContext(),
            layout = clFTSnackbar,
            message = viewModel.state.errorDescription
        )
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }


    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    private fun getBindings(): FragmentCashTransferBinding {
        return viewDataBinding as FragmentCashTransferBinding
    }

}