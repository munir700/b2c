package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical.AddSparePhysicalCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.virtual.AddSpareVirtualCardViewHelper
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels.AddSpareCardViewModel
import kotlinx.android.synthetic.main.fragment_add_spare_card.*

class AddSpareCardFragment : AddPaymentChildFragment<IAddSpareCard.ViewModel>(),
    IAddSpareCard.View {
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
                    findNavController().navigate(R.id.action_addSpareCardFragment_to_addSparePhysicalCardSuccessFragment)
                }

                viewModel.ADD_VIRTUAL_SPARE_CLICK_EVENT -> {
                    AddSpareVirtualCardViewHelper(
                        this!!.activity!!,
                        navController,
                        view,
                        viewModel
                    )
                }

                R.id.btnDoneAddingSpareVirtualCard -> {
                    activity!!.onBackPressed()
                }

            }
        })
    }

    private fun getUpArguments() {
        viewModel.cardType =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).cardType } as String
        viewModel.state.cardType = viewModel.cardType

        val physicalCardAddressTitle = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).newDeliveryAddressTitle
        } as String
        if (!physicalCardAddressTitle.isNullOrEmpty()) {
            viewModel.state.physicalCardAddressTitle = physicalCardAddressTitle
        }
        val physicalCardAddressSubTitle = arguments?.let {
            AddSpareCardFragmentArgs.fromBundle(it).newDeliveryAddressSubTitle
        } as String

        if (!physicalCardAddressSubTitle.isNullOrEmpty() && !physicalCardAddressSubTitle.equals(" ")) {
            viewModel.state.physicalCardAddressSubTitle = physicalCardAddressSubTitle
            viewModel.isFromaddressScreen = true
        }
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}