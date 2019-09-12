package co.yap.modules.dashboard.cards.home.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.modols.PaymentCard
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardsViewModel
import co.yap.modules.dashboard.fragments.YapDashboardChildFragment
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: IYapCards.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(this, Observer {

        })

        val adapter = YapCardsAdaptor(DummyCardData.dummyData)
        viewPager2.adapter = adapter

        with(viewPager2) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
        }

        val pageMarginPx = 400
        val offsetPx = 10
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
    }

    object DummyCardData {
        val dummyData: MutableList<PaymentCard>
            get() {
                val categories = mutableListOf<PaymentCard>()
                return categories.apply {
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                    add(
                        PaymentCard(
                            "1",
                            "Rose Gold Premium card",
                            "asd",
                            "200",
                            "This card is frozen",
                            "Unfreeze card"
                        )
                    )
                }
            }
    }
}