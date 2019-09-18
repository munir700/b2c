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
                viewModel.state.onChangeLocationClick = true
                navController.navigate(R.id.action_addSpareCardFragment_to_addSparePhysicalCardSuccessFragment)
            }

        })

        view.tvChangeLocation.setOnClickListener(object :
            View.OnClickListener {

            override fun onClick(v: View?) {
                viewModel.state.onChangeLocationClick = true
                val action =
                    AddSpareCardFragmentDirections.actionAddSpareCardFragmentToAddressSelectionFragment(
                        true
                    )
                navController.navigate(action)
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