package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentAddVirtualCardBinding
import co.yap.modules.dashboard.cards.addpaymentcard.main.fragments.AddPaymentChildFragment
import co.yap.widgets.CircleView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_add_virtual_card.*

class AddVirtualCardFragment() : AddPaymentChildFragment<IAddVirtualCard.ViewModel>(),
    TabLayout.OnTabSelectedListener, IAddVirtualCard.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_virtual_card
    override val viewModel: IAddVirtualCard.ViewModel
        get() = ViewModelProviders.of(this).get(AddVirtualCardViewModel::class.java)
    val adaptr: AddVirtualCardAdapter by lazy {
        AddVirtualCardAdapter(viewModel.getCardThemesOption())
    }
    private var tabViews = ArrayList<CircleView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateAdapter()
    }
    private fun initiateAdapter() {
        viewModel.adapter.set(adaptr)
        getBindings().viewPager.adapter = viewModel.adapter.get()
        setupPager()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun setupPager() {
        getBindings().viewPager?.apply {
            viewModel.state.cardDesigns?.observe(this@AddVirtualCardFragment, Observer {
                TabLayoutMediator(tabLayout, this,
                    TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                        val view =
                            layoutInflater.inflate(R.layout.item_circle_view, null) as CircleView
                        view.layoutParams = ViewGroup.LayoutParams(
                            R.dimen._24sdp,
                            R.dimen._24sdp
                        )
                        try {
                            view.circleColor = Color.parseColor(it[position].designColorCode)
                            //tab.tag = it[position]
                        } catch (e: Exception) {
                        }
                        getBindings().tabLayout.addOnTabSelectedListener(this@AddVirtualCardFragment)
                        tabViews.add(view)
                        onTabSelected(tabLayout.getTabAt(0))
                        viewModel.state.designCode?.value =
                            adaptr.getDataList()[0].designCode
                        tab.customView = view
                    }).attach()
            })
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            viewModel.state.designCode?.value =
                adaptr.getDataList()[it.position].designCode
            tabViews[it.position].borderWidth = 6f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let {
            tabViews[it.position].borderWidth = 0f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun getBindings(): FragmentAddVirtualCardBinding {
        return viewDataBinding as FragmentAddVirtualCardBinding
    }
}
