package co.yap.modules.dashboard.cards.addpaymentcard.spare.virtual.cardcolour

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
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.helpers.extentions.getColors
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_add_virtual_card.*

class AddVirtualCardFragment : AddPaymentChildFragment<IAddVirtualCard.ViewModel>(),
    TabLayout.OnTabSelectedListener,
    IAddVirtualCard.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_add_virtual_card
    override val viewModel: AddVirtualCardViewModel
        get() = ViewModelProviders.of(this).get(AddVirtualCardViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateAdapter()
    }

    private fun initiateAdapter() {
        viewModel.adapter.get()?.setList(viewModel.getCardThemesOption())
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
                            dimen(R.dimen._35sdp),
                            dimen(R.dimen._35sdp)
                        )
                        try {
                            view.circleColorStart =
                                Color.parseColor(it[position].designCodeColors?.firstOrNull()?.colorCode)
                            view.circleColorEnd =
                                Color.parseColor(it[position].designCodeColors?.get(1)?.colorCode)
                            view.circleColorDirection = CircleView.GradientDirection.TOP_TO_BOTTOM

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        getBindings().tabLayout.addOnTabSelectedListener(this@AddVirtualCardFragment)
                        viewModel.tabViews.get()?.add(view)
                        viewModel.parentViewModel?.selectedVirtualCardPosition?.get()
                            ?.let { selectedPos ->
                                viewModel.parentViewModel?.selectedVirtualCard =
                                    viewModel.adapter.get()?.getDataList()?.get(selectedPos)
                                viewModel.state.designCode?.value =
                                    viewModel.adapter.get()?.getDataList()
                                        ?.get(selectedPos)?.designCode
                                onTabSelected(tabLayout.getTabAt(selectedPos))
                                viewPager.post {
                                    viewPager.setCurrentItem(selectedPos, true)
                                }
                            }
                        tab.customView = view

                    }).attach()
            })
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
            viewModel.parentViewModel?.selectedVirtualCard =
                viewModel.adapter.get()?.getDataList()?.get(it.position)
            viewModel.state.designCode?.value =
                viewModel.adapter.get()?.getDataList()?.get(it.position)?.designCode
            viewModel.parentViewModel?.selectedVirtualCardPosition?.set(it.position)
            viewModel.tabViews.get()?.get(it.position)?.borderWidth = 8f
            viewModel.tabViews.get()?.get(it.position)?.borderColorDirection =
                CircleView.GradientDirection.TOP_TO_BOTTOM
            try {
                viewModel.tabViews.get()?.get(it.position)?.borderColorStart =
                    Color.parseColor(
                        viewModel.adapter.get()?.getDataList()
                            ?.get(it.position)?.designCodeColors?.firstOrNull()?.colorCode
                    )
                viewModel.tabViews.get()?.get(it.position)?.borderColorEnd =
                    Color.parseColor(
                        viewModel.adapter.get()?.getDataList()
                            ?.get(it.position)?.designCodeColors?.get(
                                1
                            )?.colorCode
                    )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        tab?.let {

            viewModel.tabViews.get()?.get(it.position)?.borderWidth = 0f
            viewModel.tabViews.get()?.get(it.position)?.borderColor =
                requireContext().getColors(R.color.greyLight)
        }
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun getBindings(): FragmentAddVirtualCardBinding {
        return viewDataBinding as FragmentAddVirtualCardBinding
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> { id ->
        when (id) {
            R.id.btnNext -> {
                val action =
                    AddVirtualCardFragmentDirections.actionAddVirtualCardFragmentToAddVirtualCardNameFragment()
                navigate(action)
            }
        }
    }

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }
}
