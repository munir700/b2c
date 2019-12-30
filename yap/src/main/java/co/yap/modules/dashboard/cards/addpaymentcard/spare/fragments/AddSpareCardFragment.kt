package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments

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
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity.Companion.onBackPressCheck
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels.AddSpareCardViewModel
import co.yap.modules.dashboard.cards.reordercard.activities.ReorderCardActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Strings
import co.yap.translation.Translator
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.managers.MyUserManager


class AddSpareCardFragment : AddPaymentChildFragment<IAddSpareCard.ViewModel>(),
    IAddSpareCard.View {

    private var cardAdded: Boolean = false
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_spare_card

    override val viewModel: IAddSpareCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddSpareCardViewModel::class.java)

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.avaialableCardBalance = viewModel.availableBalance
        getUpArguments()
        navController = findNavController()
        setObservers()

        if (viewModel.state.cardType == getString(R.string.screen_spare_card_landing_display_text_physical_card)) {
            AddSparePhysicalCardViewHelper(
                this.activity!!,
                navController,
                view,
                viewModel
            )
        }
    }

    private fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                viewModel.ADD_PHYSICAL_SPARE_SUCCESS_EVENT -> {
                    onAddPhysicalCard()
                }

                viewModel.ADD_VIRTUAL_SPARE_SUCCESS_EVENT -> {
                    onAddVirtualCard()
                }
                viewModel.REORDER_CARD_SUCCESS_EVENT -> {
                    onReorderCard()
                }

                R.id.btnConfirmPhysicalCardPurchase -> {
                    onPressConfirmPurchasePhysical()
                }

                R.id.btnConfirmVirtualCardPurchase -> {
                    onPressConfirmPurchaseVirtual()
                }

                R.id.btnDoneAddingSpareVirtualCard -> {
                    setupActionsIntent()
                    activity!!.finish()
                }

                R.id.btnConfirm -> {
                    viewModel.state.toggleVisibility = true
                    if (viewModel.isFromaddressScreen) {
                        viewModel.address = Address(
                            viewModel.state.physicalCardAddressSubTitle,
                            viewModel.state.physicalCardAddressTitle,
                            viewModel.latitude.toDouble(),
                            viewModel.longitude.toDouble()
                        )
                    }
                }

            }
        })
    }

    private fun onAddPhysicalCard() {
        updateTransactionOnHome()
        MyUserManager.updateCardBalance()

        if (!viewModel.isFromBlockCardScreen) {
            if (activity is AddPaymentCardActivity) {
                (activity as AddPaymentCardActivity).hideToolbar()
            }
        }
        findNavController().navigate(R.id.action_addSpareCardFragment_to_addSparePhysicalCardSuccessFragment)
    }

    private fun onAddVirtualCard() {
        updateTransactionOnHome()
        MyUserManager.updateCardBalance()
        if (!viewModel.isFromBlockCardScreen) {
            if (activity is AddPaymentCardActivity)
                (activity as AddPaymentCardActivity).hideToolbar()
        }
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

    private fun onReorderCard() {
        updateTransactionOnHome()
        showToast("Reorder Card Successfully")
//        findNavController().navigate(R.id.action_addSpareCardFragment_to_successFragment)
    }

    private fun onPressConfirmPurchaseVirtual() {
        try {
            // todo temporary logic added to fix a bug, balance should be stored as double in view model
            val virtualCardFee =
                viewModel.state.virtualCardFee.replace("AED ", "").replace(",", "")
                    .toDouble()
            val availableCardBalance =
                viewModel.state.avaialableCardBalance.replace("AED ", "")
                    .replace(",", "")
                    .toDouble()
            if (virtualCardFee > availableCardBalance) {
                showDialog()
            } else {
                viewModel.requestAddSpareVirtualCard()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onPressConfirmPurchasePhysical() {
        // todo temporary logic added to fix a bug, balance should be stored as double in view model
        try {
            val physicalCardFee =
                viewModel.state.physicalCardFee.replace("AED ", "").replace(
                    ",",
                    ""
                ).toDouble()
            val availableCardBalance =
                viewModel.state.avaialableCardBalance.replace("AED ", "").replace(
                    ",",
                    ""
                ).toDouble()

            if (physicalCardFee > availableCardBalance) {
                showDialog()
            } else {
                if (activity is ReorderCardActivity) {
                    viewModel.requestReorderCard()
                } else {
                    viewModel.requestAddSparePhysicalCard()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getUpArguments() {

        viewModel.cardType =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).cardType } as String

        viewModel.state.cardType = viewModel.cardType

//        arguments?.let { AddressSelectionFragmentArgs.fromBundle(it).isFromPhysicalCardsScreen }

        viewModel.isFromBlockCardScreen =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).isFromBlockCard } as Boolean
        viewModel.requestInitialData()

        val physicalCardAddressTitle = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).newDeliveryAddressTitle
        } as String
        if (!physicalCardAddressTitle.isNullOrEmpty()) {
            viewModel.state.physicalCardAddressSubTitle = physicalCardAddressTitle
            viewModel.state.enableConfirmLocation = true
        }
        val physicalCardAddressSubTitle = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).newDeliveryAddressSubTitle
        } as String

        if (!physicalCardAddressSubTitle.isNullOrEmpty() && physicalCardAddressSubTitle != " ") {
            viewModel.state.physicalCardAddressTitle = physicalCardAddressSubTitle
            viewModel.isFromaddressScreen = true
            viewModel.state.enableConfirmLocation = true
        }
        //

        val latitude = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).latitude
        } as String
        if (!latitude.isNullOrEmpty()) {
            viewModel.latitude = latitude
        }
        val longitude = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).longitude
        } as String
        if (!longitude.isNullOrEmpty()) {
            viewModel.longitude = longitude
        }
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
        activity?.setResult(Activity.RESULT_OK, returnIntent)
    }


    private fun showDialog() {

        val builder = AlertDialog.Builder(this.requireActivity())

        builder.setMessage(
            Translator.getString(
                context!!, Strings.screen_add_spare_card_display_text_alert_title
            )
        )
        builder.setPositiveButton(
            Translator.getString(
                context!!, Strings.screen_add_spare_card_display_button_block_alert_top_up
            )
        ) { dialog, which ->


        }

        builder.setNegativeButton(
            Translator.getString(
                context!!, Strings.screen_add_spare_card_display_button_block_alert_skip
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