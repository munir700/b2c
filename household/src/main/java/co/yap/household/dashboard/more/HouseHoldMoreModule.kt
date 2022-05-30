package co.yap.household.dashboard.more

import android.content.Context
import co.yap.R
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.helpers.ThemeColorUtils
import com.leanplum.Leanplum
import dagger.Module


@Module
class HouseHoldMoreModule {/*: BaseFragmentModule<HouseHoldMoreFragment>() {

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
    @FragmentScope*/
    fun provideMoreOptions(context: Context): MutableList<MoreOption> {
        val list = mutableListOf<MoreOption>()
        list.add(
            MoreOption(
                R.id.more_notification,
                "Notifications",
                R.drawable.ic_notification_more,
                ThemeColorUtils.colorPrimaryAltAttribute(context),
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
                ThemeColorUtils.colorPrimaryAltAttribute(context),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                R.id.more_help_support,
                "Help and support",
                R.drawable.ic_support,
                ThemeColorUtils.colorPrimaryAltAttribute(context),
                false,
                0
            )
        )
        list.add(
            MoreOption(
                R.id.more_terms_condition,
                "Terms & conditions",
                R.drawable.ic_file,
                ThemeColorUtils.colorPrimaryAltAttribute(context),
                false,
                0
            )
        )
        return list
    }
}
