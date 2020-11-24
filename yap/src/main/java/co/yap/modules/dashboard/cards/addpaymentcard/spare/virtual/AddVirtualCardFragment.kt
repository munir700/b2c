package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import android.graphics.Color
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.fragments.AddPaymentChildFragment
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment
import co.yap.yapcore.helpers.extentions.dimen
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_add_virtual_card.*

class AddVirtualCardFragment() : AddPaymentChildFragment<IAddVirtualCard.ViewModel>(), TabLayout.OnTabSelectedListener {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_virtual_card
    override val viewModel: IAddVirtualCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddVirtualCardViewModel::class.java)

    private fun setupPager() {
        viewPager?.apply {
//            this.setPageTransformer(
//                SimplePageOffsetTransformer(
//                    resources.getDimensionPixelOffset(R.dimen._30sdp),
//                    resources.getDimensionPixelOffset(R.dimen._40sdp)
//                )
//            )
            viewModel.state.cardDesigns?.observe(this@AddVirtualCardFragment, Observer {
                TabLayoutMediator(tabLayout, this,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        val view =
                            layoutInflater.inflate(R.layout.item_circle_view, null) as CircleView
                        view.layoutParams = ViewGroup.LayoutParams(
                            dimen(R.dimen._24sdp),
                            dimen(R.dimen._24sdp)
                        )
                        try {
                            view.circleColor = Color.parseColor(it[position].designColorCode)
                            //tab.tag = it[position]
                        } catch (e: Exception) {
                        }
                        tabLayout?.addOnTabSelectedListener(this@AddVirtualCardFragment)
                        tabViews.add(view)
                        onTabSelected(tabLayout.getTabAt(0))
                        viewModel.state.designCode?.value =
                            this@AddVirtualCardFragment.adapter.getData()[0].designCode
                        tab.customView = view
                    }).attach()
            })
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }
}