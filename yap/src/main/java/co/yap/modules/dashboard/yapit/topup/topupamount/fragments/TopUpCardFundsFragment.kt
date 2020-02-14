package co.yap.modules.dashboard.yapit.topup.topupamount.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopUpCardFundsBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.yapit.topup.addtopupcard.activities.AddTopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.topupamount.activities.TopUpCardActivity
import co.yap.modules.dashboard.yapit.topup.topupamount.viewModels.TopUpCardFundsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.launchActivity
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

        viewModel.htmlLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                launchActivity<AddTopUpCardActivity>(requestCode = Constants.EVENT_TOP_UP_CARD_TRANSACTION) {
                    putExtra(Constants.KEY, it)
                    putExtra(Constants.TYPE, Constants.TYPE_TOP_UP_TRANSACTION)
                }
            } else {
                return@Observer
            }

        })

        viewModel.topUpTransactionModelLiveData?.observe(this, Observer {
            if (context is TopUpCardActivity) {
                (context as TopUpCardActivity).topUpTransactionModel =
                    viewModel.topUpTransactionModelLiveData
                val action =
                    TopUpCardFundsFragmentDirections.actionTopUpCardFundsFragmentToVerifyCardCvvFragment(
                        viewModel.state.amount.toString(),
                        viewModel.state.currencyType
                    )
                findNavController().navigate(
                    action
                )
            }
        })

    }

    var clickEvent = Observer<Int> {
        when (it) {
            R.id.btnAction -> {
                viewModel.createTransactionSession()
            }
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
        } else if (viewModel.state.transactionFee.toDouble() >= 0) {
            viewModel.state.transactionFeeSpannableString =
                getString(Strings.screen_topup_transfer_display_text_transaction_fee)
                    .format(
                        viewModel.state.currencyType + " " + Utils.getFormattedCurrency(viewModel.state.transactionFee)
                    )
            getBindings().tvFeeDescription.text = Utils.getSppnableStringForAmount(
                requireContext(),
                viewModel.state.transactionFeeSpannableString ?: "",
                viewModel.state.currencyType,
                viewModel.state.transactionFee
            )
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.EVENT_TOP_UP_CARD_TRANSACTION && resultCode == Activity.RESULT_OK) {
            if (true == data?.let {
                    it.getBooleanExtra(Constants.START_POOLING, false)
                }) {
                //call api for pooling
                viewModel.startPooling(true)
            }

        }
    }


    private fun getBindings(): FragmentTopUpCardFundsBinding {
        return viewDataBinding as FragmentTopUpCardFundsBinding
    }
}