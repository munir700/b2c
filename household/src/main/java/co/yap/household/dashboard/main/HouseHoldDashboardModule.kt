package co.yap.household.dashboard.main

import co.yap.household.R
import co.yap.modules.sidemenu.ProfilePictureAdapter
import co.yap.widgets.arcmenu.FloatingActionMenu
import co.yap.widgets.arcmenu.animation.SlideInAnimationHandler
import co.yap.yapcore.helpers.extentions.dimen
import co.yap.yapcore.managers.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class HouseHoldDashboardModule /*: BaseFragmentModule<HouseholdDashboardFragment>() */{

    /*@Provides
    @ViewModelInjection
    @FragmentScope
    fun provideDashBoardVM(
        fragment: HouseholdDashboardFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldDashBoardVM>
    ) = viewModelProvider.get(fragment, HouseHoldDashBoardVM::class)*/

   /* @Provides
    fun provideHouseholdDashboardPagerAdapter(activity: FragmentActivity, childFragmentManager: FragmentManager) =
        SectionsPagerAdapter(activity, childFragmentManager)
*/
    /*@Provides
    @FragmentScope
    fun provideHouseholdHomeState(): IHouseholdDashboard.State = HouseholdDashboardState()*/

    @Provides
    fun provideProfilePictureAdapter() =
        ProfilePictureAdapter(SessionManager.usersList?.value ?: mutableListOf(), null)

    @Provides
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
                fragment.requireContext().getString(R.string.request_money),
                R.drawable.ic_request_money,
                co.yap.R.layout.component_yap_menu_sub_button,
                fragment.requireActivity(), 2
            ).setStateChangeListener(fragment)
    }
}
