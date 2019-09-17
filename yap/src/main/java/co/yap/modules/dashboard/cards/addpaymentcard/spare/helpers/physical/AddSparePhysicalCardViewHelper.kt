package co.yap.modules.dashboard.cards.addpaymentcard.spare.helpers.physical

import android.content.Context
import android.view.View
import androidx.navigation.NavController
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments.AddSpareCardFragmentDirections
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import kotlinx.android.synthetic.main.layout_add_spare_physical_card_confirm_purchase.view.*

class AddSparePhysicalCardViewHelper(
    val context: Context, val navController: NavController, val view: View,
    val viewModel: IAddSpareCard.ViewModel
) {

    init {
        view.btnConfirmPhysicalCardPurchase.setOnClickListener(object :
            View.OnClickListener {

            override fun onClick(v: View?) {
                navController.navigate(R.id.action_addSpareCardFragment_to_addSparePhysicalCardSuccessFragment)
            }

        })

        view.tvChangeLocation.setOnClickListener(object :
            View.OnClickListener {

            override fun onClick(v: View?) {
                //start location screen

                viewModel.state.toggleVisibility = true
//                navController.navigate(R.id.action_addSpareCardFragment_to_addSparePhysicalCardSuccessFragment)

                val action =
                    AddSpareCardFragmentDirections.actionAddSpareCardFragmentToAddressSelectionFragment(
                        true
                    )
                navController.navigate(action)
//
//                val action =
//                    SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
//                        getString(R.string.screen_spare_card_landing_display_text_virtual_card)
//                    )
//                navController.navigate(action)

            }

        })

        view.btnConfirm.setOnClickListener(object :
            View.OnClickListener {

            override fun onClick(v: View?) {
                viewModel.state.toggleVisibility = true
            }

        })
    }
}