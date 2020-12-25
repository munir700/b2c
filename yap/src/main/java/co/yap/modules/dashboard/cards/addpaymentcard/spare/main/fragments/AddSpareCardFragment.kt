package co.yap.modules.dashboard.cards.addpaymentcard.spare.main.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddSpareCardBinding
import co.yap.modules.dashboard.cards.addpaymentcard.main.activities.AddPaymentCardActivity.Companion.onBackPressCheck
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.main.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.main.viewmodels.AddSpareCardViewModel
import co.yap.modules.dashboard.yapit.addmoney.main.AddMoneyActivity
import co.yap.modules.dashboard.yapit.topup.cardslisting.TopUpBeneficiariesActivity
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.loadCardImage
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.managers.SessionManager
import kotlinx.android.synthetic.main.layout_add_spare_virtaul_card_confirm_purchase.*

class AddSpareCardFragment : AddPaymentChildFragment<IAddSpareCard.ViewModel>(),
    IAddSpareCard.View {

    private var cardAdded: Boolean = false
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_spare_card

    override val viewModel: AddSpareCardViewModel
        get() = ViewModelProviders.of(this).get(AddSpareCardViewModel::class.java)

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUpArguments()
        setCardDimens()
        navController = findNavController()
        setObservers()
    }

    private fun setCardDimens() {
        val params = getBinding().include.cardView.layoutParams
        params.width = getBinding().include.cardView.context.dimen(R.dimen._204sdp)
        params.height = getBinding().include.cardView.context.dimen(R.dimen._225sdp)
        getBinding().include.cardView.layoutParams = params
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                viewModel.ADD_VIRTUAL_SPARE_SUCCESS_EVENT -> {
                    onAddVirtualCard()
                }

                R.id.btnConfirmVirtualCardPurchase -> {
                    handleCoreButtonNavigation()
                }

                R.id.btnDoneAddingSpareVirtualCard -> {
                    setupActionsIntent()
                    activity?.finish()
                }
            }
        })
    }

    private fun handleCoreButtonNavigation() {
        if (btnConfirmVirtualCardPurchase.text.contains("Top up")){
            launchActivity<AddMoneyActivity> {  }
        }else{
            onPressConfirmPurchaseVirtual()
        }
    }

    private fun onAddVirtualCard() {
        updateTransactionOnHome()
        SessionManager.updateCardBalance(){}
        cardAdded = true
        view?.let {
            this.activity?.let { activity ->
                AddSpareVirtualCardViewHelper(
                    activity,
                    navController,
                    it,
                    viewModel
                )
            }
        }
    }

    private fun onPressConfirmPurchaseVirtual() {
        try {
            // todo temporary logic added to fix a bug, balance should be stored as double in view model
            val virtualCardFee =
                viewModel.state.virtualCardFee.replace(
                    "${SessionManager.getDefaultCurrency()} ",
                    ""
                ).replace(",", "")
                    .toDouble()
            val availableCardBalance =
                SessionManager.cardBalance.value?.availableBalance?.parseToDouble() ?: 0.0
            if (virtualCardFee > availableCardBalance) {
                showDialog()
            } else {
                viewModel.requestAddSpareVirtualCard()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUpArguments() {
        viewModel.cardType =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).cardType } as String
        viewModel.state.cardType = viewModel.cardType
        viewModel.cardName =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).cardName } as String
        viewModel.state.cardName = viewModel.cardName ?: ""
        viewModel.requestInitialData()
        getBinding().include.cardView.loadCardImage(viewModel.parentViewModel?.selectedVirtualCard?.frontSideDesignImage)
    }

    private fun getBinding(): FragmentAddSpareCardBinding {
        return viewDataBinding as FragmentAddSpareCardBinding
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestInitialData()
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        onBackPressCheck = true
    }

    override fun onBackPressed(): Boolean {
        return if (cardAdded) {
            return false
        } else {
            super.onBackPressed()
        }
    }

    private fun setupActionsIntent() {
        val returnIntent = Intent()
        returnIntent.putExtra("cardAdded", true)
        returnIntent.putExtra("paymentCard", viewModel.paymentCard)
        activity?.setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this.requireActivity())
        builder.setMessage(
            Translator.getString(
                requireContext(), Strings.
                    screen_add_spare_card_display_text_alert_title
            )
        )
        builder.setPositiveButton(
            Translator.getString(
                requireContext(), Strings.screen_add_spare_card_display_button_block_alert_top_up
            )
        ) { dialog, which ->
            launchActivity<TopUpBeneficiariesActivity>()
        }

        builder.setNegativeButton(
            Translator.getString(
                requireContext(), Strings.screen_add_spare_card_display_button_block_alert_skip
            )
        ) { dialog, which ->
            //dismiss
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun updateTransactionOnHome() {
        // Send Broadcast for updating transactions list in `Home Fragment`
        val intent = Intent(Constants.BROADCAST_UPDATE_TRANSACTION)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
}