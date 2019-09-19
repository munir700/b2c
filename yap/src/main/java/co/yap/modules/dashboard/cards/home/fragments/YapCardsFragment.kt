package co.yap.modules.dashboard.cards.home.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.home.adaptor.YapCardsAdaptor
import co.yap.modules.dashboard.cards.home.interfaces.IYapCards
import co.yap.modules.dashboard.cards.home.viewmodels.YapCardsViewModel
import co.yap.modules.dashboard.fragments.YapDashboardChildFragment
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.interfaces.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_yap_cards.*

class YapCardsFragment : YapDashboardChildFragment<IYapCards.ViewModel>(), IYapCards.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_yap_cards

    override val viewModel: IYapCards.ViewModel
        get() = ViewModelProviders.of(this).get(YapCardsViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        viewModel.clickEvent.observe(this, Observer {

        })

        viewModel.state.cards.observe(this, Observer {
            (viewPager2.adapter as YapCardsAdaptor).setItem(it)
        })

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
                        val action =
                            YapCardsFragmentDirections.actionYapCardsToYapCardStatusFragment(
                                viewModel.state.cards.value?.get(pos)!!
                            )
                        view.findNavController().navigate(action)
                    }
                    R.id.lySeeDetail -> {
                        showToast("Details Section")
                    }
                }
            }
        })
    }
}