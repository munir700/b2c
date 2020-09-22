package co.yap.household.dashboard.more

import co.yap.R
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.helpers.ThemeColorUtils
import com.leanplum.Leanplum
import dagger.Module
import dagger.Provides


@Module
class HouseHoldMoreModule : BaseFragmentModule<HouseHoldMoreFragment>() {

    @Provides
    @ViewModelInjection
    @FragmentScope
    fun provideHouseHoldMoreVM(
        fragment: HouseHoldMoreFragment,
        viewModelProvider: InjectionViewModelProvider<HouseHoldMoreVM>
    ): HouseHoldMoreVM = viewModelProvider.get(fragment, HouseHoldMoreVM::class)

    @Provides
    @FragmentScope
    fun provideHouseHoldMoreState(): IHouseHoldMore.State = HouseHoldMoreState()

    @Provides
    @FragmentScope
    fun provideHouseHoldMoreAdapter(optionList: MutableList<MoreOption>) =
        HouseHoldMoreFragment.Adapter(optionList, null)

    @Provides
    @FragmentScope
    fun provideMoreOptions(fragment: HouseHoldMoreFragment): MutableList<MoreOption> {
        val list = mutableListOf<MoreOption>()
        list.add(
            MoreOption(
                R.id.more_notification,
                "Notifications",
                R.drawable.ic_notification_more,
                ThemeColorUtils.colorPrimaryAltAttribute(fragment.requireContext()),
                false,
                Leanplum.getInbox().unreadCount()
            )
        )
        //colorSecondaryGreen
        list.add(
            MoreOption(
                R.id.more_atm_cdm,
                "Locate ATM and CDM",
                R.drawable.ic_home_more,
                ThemeColorUtils.colorPrimaryAltAttribute(fragment.requireContext()),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                R.id.more_help_support,
                "Help and support",
                R.drawable.ic_support,
                ThemeColorUtils.colorPrimaryAltAttribute(fragment.requireContext()),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                R.id.more_terms_condition,
                "Terms & conditions",
                R.drawable.ic_file,
                ThemeColorUtils.colorPrimaryAltAttribute(fragment.requireContext()),
                false,
                0
            )
        )
        return list
    }
}
