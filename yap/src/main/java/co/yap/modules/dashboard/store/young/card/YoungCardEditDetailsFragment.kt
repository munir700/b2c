package co.yap.modules.dashboard.store.young.card

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungCardEditDetailsBinding
import co.yap.translation.Strings
import co.yap.widgets.CircleView
import co.yap.widgets.viewpager.SimplePageOffsetTransformer
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_young_card_edit_details.*
import javax.inject.Inject

@AndroidEntryPoint
class YoungCardEditDetailsFragment :
    BaseNavViewModelFragmentV2<FragmentYoungCardEditDetailsBinding, IYoungCardEditDetails.State, YoungCardEditDetailsVM>(),
    TabLayout.OnTabSelectedListener {
    @Inject
    lateinit var adapter: YoungCardEditAdapter
    private var tabViews = ArrayList<CircleView>()

    override val viewModel: YoungCardEditDetailsVM by viewModels()

    override fun getToolBarTitle() = getString(
        Strings.screen_young_card_toolbar_text,
        viewModel.state.childName.value ?: ""
    )

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_card_edit_details
    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                navigate(YoungCardEditDetailsFragmentDirections.actionYoungCardEditDetailsFragmentToYoungCreatePinCodeFragment())
            }
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.adapter?.set(adapter)
        viewPager?.adapter = adapter
        setupPager()

    }

    private fun setupPager() {
        viewPager?.apply {
//            this.setPageTransformer(
//                SimplePageOffsetTransformer(
//                    resources.getDimensionPixelOffset(R.dimen._30sdp),
//                    resources.getDimensionPixelOffset(R.dimen._40sdp)
//                )
//            )
            viewModel.state.cardDesigns?.observe(this@YoungCardEditDetailsFragment, Observer {
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
                        tabLayout?.addOnTabSelectedListener(this@YoungCardEditDetailsFragment)
                        tabViews.add(view)
                        onTabSelected(tabLayout.getTabAt(0))
                        viewModel.state.designCode?.value =
                            this@YoungCardEditDetailsFragment.adapter.getData()[0].designCode
                        tab.customView = view
                    }).attach()
            })
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let {
            tabViews[it.position].borderWidth = 0f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            viewModel.state.designCode?.value =
                adapter.getData()[it.position].designCode// (tab.tag as HouseHoldCardsDesign).designCode
            tabViews[it.position].borderWidth = 6f
            tabViews[it.position].borderColor = Color.parseColor("#88848D")
        }
    }
}