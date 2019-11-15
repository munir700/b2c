package co.yap.modules.dashboard.yapit.topup.main.topupamount.fragments

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopUpCardFundsBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.yapit.topup.main.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels.TopUpCardFundsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager

class TopUpCardFundsFragment : BaseBindingFragment<IFundActions.ViewModel>(),
    IFundActions.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_top_up_card_funds

    override val viewModel: IFundActions.ViewModel
        get() = ViewModelProviders.of(this).get(TopUpCardFundsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
        viewModel.firstDenominationClickEvent.observe(this, Observer {
            Utils.hideKeyboard(view)
            getBindings().etAmount.setText("")
            getBindings().etAmount.append(viewModel.state.denominationAmount)
            val position = getBindings().etAmount.length()
            getBindings().etAmount.setSelection(position)
            getBindings().etAmount.clearFocus()
        })
        viewModel.secondDenominationClickEvent.observe(this, Observer {
            Utils.hideKeyboard(view)
            getBindings().etAmount.setText("")
            getBindings().etAmount.append(viewModel.state.denominationAmount)
            val position = getBindings().etAmount.length()
            getBindings().etAmount.setSelection(position)
            getBindings().etAmount.clearFocus()
        })
        viewModel.thirdDenominationClickEvent.observe(this, Observer {
            Utils.hideKeyboard(view)
            getBindings().etAmount.setText("")
            getBindings().etAmount.append(viewModel.state.denominationAmount)
            val position = getBindings().etAmount.length()
            getBindings().etAmount.setSelection(position)
            getBindings().etAmount.clearFocus()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is TopUpCardActivity)
            (activity as TopUpCardActivity).cardInfo?.let {
                viewModel.initateVM(it)
            }
        setupData()
    }


    override fun setObservers() {

        viewModel.clickEvent.observe(this, clickEvent)
        viewModel.errorEvent.observe(this, Observer {
            showErrorSnackBar()
        })


    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                //call api here
                viewModel.createTransactionSession()
                //findNavController().navigate(R.id.action_topUpCardFundsFragment_to_verifyCardCvvFragment)
            }
            //100 -> showToast("i am success")
            R.id.ivCross -> activity?.finish()
            Constants.CARD_FEE -> setUpFeeData()
        }
    }

    private fun showErrorSnackBar() {
        CustomSnackbar.showErrorCustomSnackbar(
            context = requireContext(),
            layout = getBindings().clSnackbar,
            message = viewModel.state.errorDescription
        )
    }

    private fun setupData() {
        getBindings().etAmount.filters =
            arrayOf(InputFilter.LengthFilter(7), DecimalDigitsInputFilter(2))
        if (context is TopUpCardActivity) {
            viewModel.state.cardNumber = (context as TopUpCardActivity).cardInfo?.number.toString()
            viewModel.state.cardName = (context as TopUpCardActivity).cardInfo?.alias.toString()
        }

        viewModel.state.availableBalance =
            MyUserManager.cardBalance.value?.availableBalance.toString()

        getBindings().tvAvailableBalanceGuide.text = Utils.getSppnableStringForAmount(
            requireContext(),
            viewModel.state.availableBalanceGuide,
            viewModel.state.currencyType,
            Utils.getFormattedCurrencyWithoutComma(viewModel.state.availableBalance)
        )
    }

    private fun setUpFeeData() {
        if (viewModel.state.transactionFee == getString(Strings.screen_topup_transfer_display_text_transaction_no_fee)) {
            viewModel.state.transactionFeeSpannableString =
                getString(Strings.screen_topup_transfer_display_text_transaction_fee)
                    .format(viewModel.state.transactionFee)
            getBindings().tvFeeDescription.text = Utils.getSpannableString(
                requireContext(),
                viewModel.state.transactionFeeSpannableString,
                viewModel.state.transactionFee
            )
        } else if (viewModel.state.transactionFee.toDouble() > 0) {
            viewModel.state.transactionFeeSpannableString =
                getString(Strings.screen_topup_transfer_display_text_transaction_fee)
                    .format(
                        viewModel.state.currencyType + " " + Utils.getFormattedCurrency(viewModel.state.transactionFee)
                    )
            getBindings().tvFeeDescription.text = Utils.getSppnableStringForAmount(
                requireContext(),
                viewModel.state.transactionFeeSpannableString!!,
                viewModel.state.currencyType,
                viewModel.state.transactionFee
            )
        }
    }

    private fun getBindings(): FragmentTopUpCardFundsBinding {
        return viewDataBinding as FragmentTopUpCardFundsBinding
    }
}