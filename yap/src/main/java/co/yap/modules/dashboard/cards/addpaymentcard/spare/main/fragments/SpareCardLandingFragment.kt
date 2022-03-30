package co.yap.modules.dashboard.cards.addpaymentcard.spare.main.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.main.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.cards.addpaymentcard.models.BenefitsModel
import co.yap.modules.dashboard.cards.addpaymentcard.spare.SpareCardsLandingAdapter
import co.yap.modules.dashboard.cards.addpaymentcard.spare.main.interfaces.ISpareCards
import co.yap.modules.dashboard.cards.addpaymentcard.spare.main.viewmodels.SpareCardLandingViewModel
import co.yap.yapcore.constants.Constants.KEY_AVAILABLE_BALANCE
import co.yap.yapcore.helpers.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_spare_card_landing.*


class SpareCardLandingFragment : AddPaymentChildFragment<ISpareCards.ViewModel>(), ISpareCards.View,
    SpareCardsLandingAdapter.OnItemClickedListener {
    val args: SpareCardLandingFragmentArgs? by navArgs()
    override fun onItemClick(benefitsModel: BenefitsModel) {}

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_spare_card_landing

    override val viewModel: SpareCardLandingViewModel
        get() = ViewModelProviders.of(this).get(SpareCardLandingViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getVirtualCardFee()
        setDefaultStates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addBenefitRecyclerView()
        SharedPreferenceManager.getInstance(requireActivity()).removeValue(KEY_AVAILABLE_BALANCE)

        ViewModelProviders.of(requireActivity())
            .get(AddPaymentCardViewModel::class.java).state.tootlBarTitle =
            "Add a virtual spare card"
        playCardAnimation()
        setObservers()
    }

    private fun gotoAddSpareVirtualCardConfirmScreen() {
        val action =
            SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                getString(R.string.screen_spare_card_landing_display_text_virtual_card),
                "",
                "",
                "",
                "",
                false
            )
        navigate(action)
    }

    private fun addBenefitRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        rvBenefits.layoutManager = layoutManager
        rvBenefits.isNestedScrollingEnabled = false
        rvBenefits.adapter =
            SpareCardsLandingAdapter(
                viewModel.loadJSONDummyList(),
                null
            )
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.addSpareCard -> {
                    args?.let { arg ->
                        if (arg.landedFrom.isNotEmpty()) {
                            when (arg.landedFrom) {
                                "AddVirtualCardFragment" -> navigateToNext(
                                    SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddVirtualCardFragment()
                                )
                                "AddVirtualCardNameFragment" -> navigateToNext(
                                    SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddVirtualCardNameFragment()
                                )
                                "AddSpareCardFragment" -> navigateToNext(
                                    SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                                        cardType = "",
                                        isFromBlockCard = false
                                    )
                                )
                            }
                        } else {
                            navigateToNext(SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddVirtualCardFragment())
                        }
                    }
                        ?: navigateToNext(SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddVirtualCardFragment())
                    //gotoAddVirtualCardScreen()
                }
                R.id.llAddVirtualCard -> {
                    gotoAddSpareVirtualCardConfirmScreen()
                }

                R.id.llAddPhysicalCard -> {

                    val action =
                        SpareCardLandingFragmentDirections.actionSpareCardLandingFragmentToAddSpareCardFragment(
                            getString(R.string.screen_spare_card_landing_display_text_physical_card),
                            "",
                            "",
                            "",
                            "",
                            false
                        )
                    findNavController().navigate(action)
                }
                R.id.ivCross -> {
                    activity?.onBackPressed()
                }
            }
        })
        viewModel.errorEvent.observe(this, Observer {
            requireActivity().finish()
        })
        viewModel.isFeeReceived.observe(viewLifecycleOwner, Observer {
            if (it) viewModel.updateFees("")
        })
        viewModel.updatedFee.observe(viewLifecycleOwner, Observer {
            viewModel.state.virtualCardFee = it
            viewModel.parentViewModel?.virtualCardFee = it
        })
    }

    private fun setDefaultStates() {
        args?.let { arg ->
            if (arg.landedFrom.isEmpty()) {
                viewModel.parentViewModel?.getVirtualCardDesigns {
                    if (!viewModel.parentViewModel?.virtualCardDesignsList.isNullOrEmpty()) {
                        addSpareCard.enableButton(true)
                        viewModel.parentViewModel?.selectedVirtualCard =
                            viewModel.parentViewModel?.virtualCardDesignsList?.firstOrNull()
                        viewModel.state.cardImageUrl =
                            viewModel.parentViewModel?.virtualCardDesignsList?.firstOrNull()?.frontSideDesignImage
                                ?: ""
                    } else {
                        addSpareCard.enableButton(false)
                    }
                }
            }
        }
    }

    private fun playCardAnimation() {
        lav_cards.progress = 0f
        lav_cards.playAnimation()
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObservers(this)
        viewModel.errorEvent.removeObservers(this)
    }

    override fun onDestroyView() {
        removeObservers()
        super.onDestroyView()
    }
}

