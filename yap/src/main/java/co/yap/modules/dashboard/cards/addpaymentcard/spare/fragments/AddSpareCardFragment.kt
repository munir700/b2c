package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cardType =
            arguments?.let { AddSpareCardFragmentArgs.fromBundle(it).cardType } as String
        val navController: NavController = findNavController()

        viewModel.state.cardType = viewModel.cardType
        if (viewModel.cardType.equals(getString(R.string.screen_spare_card_landing_display_text_virtual_card))) {
            layoutPhysicalCardConfirmPurchase.visibility = View.GONE
            layoutVirtualCardConfirmPurchase.visibility = View.VISIBLE

            AddSpareVirtualCardViewHelper(
                this!!.activity!!,
                navController,
                view,
                viewModel
            )
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
    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}