package co.yap.modules.dashboard.yapit.topup.main.topupamount.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentTopUpCardFundsBinding
import co.yap.modules.dashboard.cards.paymentcarddetail.addfunds.interfaces.IFundActions
import co.yap.modules.dashboard.yapit.topup.main.topupamount.viewModels.TopUpCardFundsViewModel
import co.yap.translation.Strings
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.helpers.CustomSnackbar
import co.yap.yapcore.helpers.Utils

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
            R.id.btnAction -> findNavController().navigate(R.id.action_topUpCardFundsFragment_to_verifyCardCvvFragment)
            R.id.ivCross -> activity?.finish()
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
        viewModel.state.cardName = "Citi Bank Card"
        viewModel.state.cardNumber = "123456789012"
        viewModel.state.availableBalance = Utils.getFormattedCurrency("500")

        getBindings().tvAvailableBalanceGuide.text = Utils.getSppnableStringForAmount(
            requireContext(),
            viewModel.state.availableBalanceGuide,
            viewModel.state.currencyType,
            viewModel.state.availableBalance
        )


        if (viewModel.state.transactionFee == "No Fee") {
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
                        viewModel.state.currencyType+" " + Utils.getFormattedCurrency(viewModel.state.transactionFee)
                    )
            getBindings().tvFeeDescription.text = Utils.getSppnableStringForAmount(
                requireContext(),
                viewModel.state.transactionFeeSpannableString!!,
                viewModel.state.currencyType,
                viewModel.state.transactionFee
            )
        }


        /* val card: Card = intent.getParcelableExtra(CARD)
          viewModel.state.cardNumber = card.maskedCardNo
          viewModel.cardSerialNumber = card.cardSerialNumber
          if (Constants.CARD_TYPE_PREPAID == card?.cardType) {
              if(card?.physical!!){
                  viewModel.state.cardName = Constants.TEXT_SPARE_CARD_PHYSICAL
              }else{
                  viewModel.state.cardName = Constants.TEXT_SPARE_CARD_VIRTUAL
              }
          }
           viewModel.state.availableBalance = card.availableBalance
          */

//        viewModel.state.availableBalance =  MyUserManager.cardBalance.value?.availableBalance.toString()
        /* viewModel.state.availableBalanceText =
             " " + getString(Strings.common_text_currency_type) + " " + Utils.getFormattedCurrency(
                 viewModel.state.availableBalance
             )*/
    }

    private fun getBindings(): FragmentTopUpCardFundsBinding {
        return viewDataBinding as FragmentTopUpCardFundsBinding
    }
}