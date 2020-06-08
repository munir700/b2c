package co.yap.household.dashboard.main

import android.view.View
import co.yap.household.R
import co.yap.household.dashboard.main.menu.ProfilePictureAdapter
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.adpters.SectionsPagerAdapter
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.helpers.extentions.bind
import co.yap.yapcore.helpers.extentions.bindView
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.managers.MyUserManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.Module
import dagger.Provides

@Module
class HouseHoldDashboardModule : BaseFragmentModule<HouseholdDashboardFragment>() {

    @Provides
    @ViewModelInjection
    @FragmentScope
    fun provideDashBoardVM(
        fragment: HouseholdDashboardFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldDashBoardVM>
    ) = viewModelProvider.get(fragment, HouseHoldDashBoardVM::class)

    @Provides
    @FragmentScope
    fun provideHouseholdDashboardPagerAdapter(fragment: HouseholdDashboardFragment) =
        SectionsPagerAdapter(fragment.requireActivity(), fragment.childFragmentManager)

    @Provides
    @FragmentScope
    fun provideHouseholdHomeState(): IHouseholdDashboard.State = HouseholdDashboardState()

    @Provides
    @FragmentScope
    fun provideProfilePictureAdapter() =
        ProfilePictureAdapter(MyUserManager.usersList?.value ?: mutableListOf(), null)

    @Provides
    @FragmentScope
    fun provideFloatingActionMenu(fragment: HouseholdDashboardFragment): FloatingActionMenu.Builder {
        return FloatingActionMenu.Builder(fragment.requireActivity())
            .setStartAngle(0)
            .setEndAngle(-180).setRadius(fragment.requireContext().dimen(R.dimen._69sdp))
            .setAnimationHandler(SlideInAnimationHandler())
            .addSubActionView(
                fragment.requireContext().getString(R.string.send_money),
                co.yap.R.drawable.ic_send_money,
                co.yap.R.layout.component_yap_menu_sub_button,
                fragment.requireActivity(), 1
            )
            .addSubActionView(
                "Request money",
                co.yap.R.drawable.ic_cash,
                co.yap.R.layout.component_yap_menu_sub_button,
                fragment.requireActivity(), 2
            ).setStateChangeListener(fragment)
    }
}
