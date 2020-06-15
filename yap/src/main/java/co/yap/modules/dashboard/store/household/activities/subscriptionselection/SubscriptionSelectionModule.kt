package co.yap.modules.dashboard.store.household.activities.subscriptionselection

import co.yap.R
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.translation.Strings
import co.yap.translation.Translator.getString
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class SubscriptionSelectionModule : BaseFragmentModule<SubscriptionSelectionFragment>() {

    @Provides
    @ViewModelInjection
    fun provideMyCardVM(
        fragment: SubscriptionSelectionFragment,
        viewModelProvider: InjectionViewModelProvider<SubscriptionSelectionVM>
    ): SubscriptionSelectionVM = viewModelProvider.get(fragment, SubscriptionSelectionVM::class)

    @Provides
    fun provideMyCardTransactionsAdapter() =
        SubscriptionSelectionFragment.Adapter(
            ArrayList(),
            null
        )

    @Provides
    @FragmentScope
    fun provideMyCardState(): ISubscriptionSelection.State = SubscriptionSelectionState()


    @Provides
    @FragmentScope
    fun generateB2CPages(context: SubscriptionSelectionFragment): ArrayList<WelcomeContent> {
        val content1 = WelcomeContent(
            getString(
                context.requireContext(),
                Strings.screen_welcome_b2c_display_text_page1_title
            ),
            getString(
                context.requireContext(),
                Strings.screen_yap_house_hold_success_display_text_pager_color
            ),
            R.drawable.image_yap_household_card
        )
        val content2 = WelcomeContent(
            getString(
                context.requireContext(),
                Strings.screen_welcome_b2c_display_text_page2_title
            ),
            getString(
                context.requireContext(),
                Strings.screen_yap_house_hold_success_display_text_pager_schedule_payments
            ),
            R.drawable.image_yap_household_salary
        )
        val content3 = WelcomeContent(
            getString(
                context.requireContext(),
                Strings.screen_welcome_b2c_display_text_page3_title
            ),
            getString(
                context.requireContext(),
                Strings.screen_yap_house_hold_success_display_text_pager_schedule_pots
            ),
            R.drawable.image_hosue_hold_track_expenses
        )
        return arrayListOf(content1, content2, content3)
    }
}
