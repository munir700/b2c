package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.activities.AddPaymentCardActivity.Companion.onBackPressCheck
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels.AddSpareCardViewModel
import co.yap.networking.cards.responsedtos.Address
import co.yap.translation.Strings
import co.yap.translation.Translator
import kotlinx.android.synthetic.main.fragment_add_spare_card.*


class AddSpareCardFragment : AddPaymentChildFragment<IAddSpareCard.ViewModel>(),
    IAddSpareCard.View {

    private var cardAdded: Boolean = false
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_add_spare_card

    override val viewModel: IAddSpareCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddSpareCardViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.avaialableCardBalance = viewModel.availableBalance.toString()
        getUpArguments()

        val navController: NavController = findNavController()

        if (viewModel.cardType.equals(getString(R.string.screen_spare_card_landing_display_text_virtual_card))) {
            layoutPhysicalCardConfirmPurchase.visibility = View.GONE
            layoutVirtualCardConfirmPurchase.visibility = View.VISIBLE


        } else if (viewModel.cardType.equals(getString(R.string.screen_spare_card_landing_display_text_physical_card))) {
            layoutVirtualCardConfirmPurchase.visibility = View.GONE
            layoutPhysicalCardConfirmPurchase.visibility = View.VISIBLE
            AddSparePhysicalCardViewHelper(
                this!!.activity!!,
                navController,
                view,
                viewModel
            )
        }

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                viewModel.ADD_PHYSICAL_SPARE_CLICK_EVENT -> {
//                    if (!viewModel.isFromBlockCardScreen ){
////                        (activity as AddPaymentCardActivity).hideToolbar()
////                    }else{
//                        (activity as AddPaymentCardActivity).hideToolbar()
//                    }

                    findNavController().navigate(R.id.action_addSpareCardFragment_to_addSparePhysicalCardSuccessFragment)
                }

                viewModel.ADD_VIRTUAL_SPARE_CLICK_EVENT -> {
//                    if (!viewModel.isFromBlockCardScreen ){
//                        (activity as AddPaymentCardActivity).hideToolbar()
//                    }
                    cardAdded = true
                    AddSpareVirtualCardViewHelper(
                        this!!.activity!!,
                        navController,
                        view,
                        viewModel
                    )
                }

                viewModel.CONFIRM_PURCHASE -> {

                    if (viewModel.state.physicalCardFee > viewModel.state.avaialableCardBalance) {
                        showDialog()
                    } else {
                        viewModel.requestAddSparePhysicalCard()
                    }
                }

                R.id.btnDoneAddingSpareVirtualCard -> {
                    // Spare virtual card added event
                    setupActionsIntent()
                }

                R.id.btnConfirm -> {
//                      if (viewModel.state.physicalCardFee > viewModel.state.avaialableCardBalance) {
//                        showDialog()
//                    } else {
                    viewModel.state.toggleVisibility = true
//
                    if (viewModel.isFromaddressScreen) {
                        viewModel.address = Address(
                            viewModel.state.physicalCardAddressSubTitle,
                            viewModel.state.physicalCardAddressTitle,
                            viewModel.latitude.toDouble(),
                            viewModel.longitude.toDouble()
                        )
                    }
//                    }
                }

            }
        })
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
        }
        val physicalCardAddressSubTitle = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).newDeliveryAddressSubTitle
        } as String

        if (!physicalCardAddressSubTitle.isNullOrEmpty() && !physicalCardAddressSubTitle.equals(" ")) {
            viewModel.state.physicalCardAddressTitle = physicalCardAddressSubTitle
            viewModel.isFromaddressScreen = true
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
            setupActionsIntent()
            activity!!.finish()
            true
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

}