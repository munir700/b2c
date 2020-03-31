package co.yap.household.dashboard.main

import co.yap.household.BR
import co.yap.household.R
import co.yap.household.dashboard.home.HouseholdHomeFragment
import co.yap.household.databinding.ActivityHouseholdDashboardBinding
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.base.BaseViewModelActivity
import co.yap.yapcore.helpers.extentions.dimen
import kotlinx.android.synthetic.main.activity_household_dashboard.*
import javax.inject.Inject

class HouseholdDashboardActivity :
    BaseViewModelActivity<ActivityHouseholdDashboardBinding, IHouseholdDashboard.State, HouseHoldDashBoardVM>() {
    @Inject
    lateinit var adapter: SectionsPagerAdapter
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.activity_household_dashboard
    private var actionMenu: FloatingActionMenu? = null

    override fun postExecutePendingBindings() {
        super.postExecutePendingBindings()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()
        adapter.addFragmentInfo<HouseholdHomeFragment>()

        viewModel.adapter.set(adapter)
        bottomNav.setUpWithViewPager(viewPager)
        setupYapItButton()
    }

    private fun setupYapItButton() {
        actionMenu = FloatingActionMenu.Builder(this)
            .setStartAngle(0)
            .setEndAngle(-180).setRadius(dimen(co.yap.R.dimen._69sdp))
            .setAnimationHandler(SlideInAnimationHandler())
            .addSubActionView(
                getString(co.yap.R.string.yap_to_yap),
                co.yap.R.drawable.ic_yap_to_yap,
                co.yap.R.layout.component_yap_menu_sub_button,
                this, 1
            )
            .addSubActionView(
                getString(co.yap.R.string.top_up),
                co.yap.R.drawable.ic_top_up,
                co.yap.R.layout.component_yap_menu_sub_button,
                this, 2
            )
            .attachTo(mViewDataBinding.ivYapIt).setAlphaOverlay(mViewDataBinding.flAlphaOverlay)
            .setTxtYapIt(mViewDataBinding.txtYapIt)
            .setStateChangeListener(object :
                FloatingActionMenu.MenuStateChangeListener {
                override fun onMenuOpened(menu: FloatingActionMenu) {

                }

                override fun onMenuClosed(menu: FloatingActionMenu, subActionButtonId: Int) {
                    when (subActionButtonId) {
                        1 -> {

                            showToast("Account activation pending 1")

                        }
                        2 -> {
                            showToast("Account activation pending 2")
                        }
                    }
                }
            })
            .build()
    }
}