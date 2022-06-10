package co.yap.household.dashboard.main

import androidx.fragment.app.Fragment
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

@Module
@InstallIn(FragmentComponent::class)
class HouseHoldDashboardModule {

    @Provides
    @FragmentScoped
    fun provideProfilePictureAdapter() =
        ProfilePictureAdapter(SessionManager.usersList?.value ?: mutableListOf(), null)

    @Provides
    @FragmentScoped
    fun provideFloatingActionMenu(fragment: Fragment): FloatingActionMenu.Builder {
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
            )
    }
}
