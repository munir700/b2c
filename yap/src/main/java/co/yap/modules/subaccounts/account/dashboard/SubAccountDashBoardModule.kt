package co.yap.modules.subaccounts.account.dashboard

import androidx.fragment.app.Fragment
import co.yap.yapcore.adapters.SectionsPagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class SubAccountDashBoardModule{

    @Provides
    @FragmentScoped
    fun provideSubAccountDashBoardPagerAdapter(
        fragment: Fragment
    ) = SectionsPagerAdapter(fragment.requireActivity(), fragment.childFragmentManager)

}
