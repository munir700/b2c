package co.yap.modules.dashboard.cards.home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardsViewModel
import co.yap.modules.dashboard.cards.paymentcarddetail.activities.PaymentCardDetailActivity
import co.yap.modules.dashboard.cards.paymentcarddetail.limits.activities.CardLimitsActivity
import co.yap.modules.dashboard.fragments.YapDashboardChildFragment
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.enums.CardStatus
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {

    val EVENT_PAYMENT_CARD_DETAIL: Int get() = 11
    var selectedCardPosition : Int = 0

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: IYapCards.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        viewModel.clickEvent.observe(this, observer)

        viewModel.state.cards.observe(this, Observer {
            (viewPager2.adapter as YapCardsAdaptor).setItem(it)
        })
    }

    val observer = Observer<Int> {
        when (it) {
            R.id.tbBtnAddCard -> {
                findNavController().navigate(R.id.action_yapCards_to_addPaymentCardActivity)
            }
        }
    }

    private fun setupPager() {
        val adapter = YapCardsAdaptor(requireContext(), mutableListOf())
        viewPager2.adapter = adapter

        with(viewPager2) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        val pageMarginPx = Utils.getDimensionInPercent(context!!, true, 14)
        val offsetPx = Utils.getDimensionInPercent(context!!, true, 14)
        viewPager2.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }

        adapter.setItemListener(object : OnItemClickListener {
            override fun onItemClick(view: View, data: Any, pos: Int) {
                when (view.id) {
                    R.id.imgCard -> {
                        if (getCard(pos).active) {
                            selectedCardPosition = pos
                            startActivityForResult(
                                PaymentCardDetailActivity.newIntent(
                                    requireContext(),
                                    getCard(pos)
                                ), EVENT_PAYMENT_CARD_DETAIL
                            )
                        } else {
                            val action =
                                YapCardsFragmentDirections.actionYapCardsToYapCardStatusFragment(
                                    getCard(pos)
                                )
                            view.findNavController().navigate(action)
                        }

                    }
                    R.id.lySeeDetail -> {
                        selectedCardPosition = pos
                        startActivityForResult(
                            PaymentCardDetailActivity.newIntent(
                                requireContext(),
                                getCard(pos)
                            ), EVENT_PAYMENT_CARD_DETAIL
                        )
                    }
                    else -> {
                        if (getCard(pos).cardName == "addCard")
                            findNavController().navigate(R.id.action_yapCards_to_addPaymentCardActivity)
                    }

                }
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EVENT_PAYMENT_CARD_DETAIL -> {
                val cardNameUpdated = data?.getBooleanExtra("cardNameUpdated", false)
                val updatedCardName = data?.getStringExtra("updatedCardName")
                val cardFreezeUnfreeze = data?.getBooleanExtra("cardFreezeUnfreeze", false)
                val cardBlocked = data?.getBooleanExtra("cardBlocked", false)
                val cardRemoved = data?.getBooleanExtra("cardRemoved", false)

                if(cardNameUpdated!!){
                    viewModel.state.cards.value?.get(selectedCardPosition)?.cardName = updatedCardName.toString()
                    viewPager2.adapter?.notifyDataSetChanged()
                }

                if(cardFreezeUnfreeze!!){
                    if (cardBlocked != null) {
                        viewModel.state.cards.value?.get(selectedCardPosition)?.blocked = cardBlocked
                        viewModel.state.cards.value?.get(selectedCardPosition)?.status = "BLOCKED"
                    }
                    viewPager2.adapter?.notifyDataSetChanged()
                }

                if(cardRemoved!!){
                    viewModel.state.cards.value?.clear()
                    viewModel.getCards()
                }

                }
            }
        }

    fun getCard(pos: Int): Card {
        return viewModel.state.cards.value?.get(pos)!!
    }
}