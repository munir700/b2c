package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.spare.interfaces.IAddSpareCard
import co.yap.modules.dashboard.cards.addpaymentcard.spare.viewmodels.AddSpareCardViewModel

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

        viewModel.state.cardType = viewModel.cardType
        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.btnDone -> {
// hide tha success layout or kill this fragment and go back

//                    val action =
//                        SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
//                            getString(R.string.screen_spare_card_landing_display_text_virtual_card)
//                        )
//                    findNavController().navigate(action)


                }


            }
        })
    }

}