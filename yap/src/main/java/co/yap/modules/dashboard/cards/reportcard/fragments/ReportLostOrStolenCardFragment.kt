package co.yap.modules.dashboard.cards.reportcard.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.reportcard.interfaces.IRepostOrStolenCard
import co.yap.modules.dashboard.cards.reportcard.viewmodels.ReportLostOrStolenCardViewModels
import co.yap.yapcore.BaseBindingFragment

//class ReportLostOrStolenCardFragment : BaseBindingFragment<IRepostOrStolenCard.ViewModel>(),
//    IRepostOrStolenCard.View {

//    }

class ReportLostOrStolenCardFragment : ReportOrLOstCardChildFragment<IRepostOrStolenCard.ViewModel>(), IRepostOrStolenCard.View{

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_lost_or_stolen_card

    override val viewModel: IRepostOrStolenCard.ViewModel
        get() = ViewModelProviders.of(this).get(ReportLostOrStolenCardViewModels::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        SharedPreferenceManager(this!!.context!!).removeValue(SharedPreferenceManager.KEY_AVAILABLE_BALANCE)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

               /* R.id.llAddVirtualCard -> {
                    val action =
                        ReportLostOrStolenCardFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                            getString(R.string.screen_spare_card_landing_display_text_virtual_card)
                        )
                    findNavController().navigate(action)


                }

                R.id.llAddPhysicalCard -> {

                    val action =
                        ReportLostOrStolenCardFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                            getString(R.string.screen_spare_card_landing_display_text_physical_card)
                        )
                    findNavController().navigate(action)
                }*/

            }
        })


    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}