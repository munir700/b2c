package co.yap.modules.dashboard.cards.addpaymentcard.spare.fragments


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.ISpareCards
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.cards.addpaymentcard.spare.SpareCardsLandingAdapter
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.SpareCardLandingViewModel
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_spare_card_landing.*


class SpareCardLandingFragment : AddPaymentChildFragment<ISpareCards.ViewModel>(), ISpareCards.View,
    SpareCardsLandingAdapter.OnItemClickedListener {

    override fun onItemClick(benefitsModel: BenefitsModel) {
        val action =
            SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToBenefitsFragment(
                benefitsModel
            )
        findNavController().navigate(action)

    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_spare_card_landing

    override val viewModel: ISpareCards.ViewModel
        get() = ViewModelProviders.of(this).get(SpareCardLandingViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addBenefitRecyclerView()

        SharedPreferenceManager(this!!.context!!).removeValue(SharedPreferenceManager.KEY_AVAILABLE_BALANCE)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.llAddVirtualCard -> {
                     val action =
                        SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                            getString(R.string.screen_spare_card_landing_display_text_virtual_card)
                        )
                    findNavController().navigate(action)


                }

                R.id.llAddPhysicalCard -> {

                    val action =
                        SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                            getString(R.string.screen_spare_card_landing_display_text_physical_card)
                        )
                    findNavController().navigate(action)
                 }

            }
        })


    }

    private fun addBenefitRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvBenefits.layoutManager = layoutManager
        rvBenefits.adapter =
            SpareCardsLandingAdapter(
                viewModel.loadJSONDummyList(),
                this
            )
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