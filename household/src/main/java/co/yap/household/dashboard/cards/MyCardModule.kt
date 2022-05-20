package co.yap.household.dashboard.cards

import androidx.recyclerview.widget.RecyclerView
import co.yap.household.R
import co.yap.household.dashboard.home.HomeTransactionAdapter
import co.yap.translation.Strings
import co.yap.translation.Translator.getString
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import com.arthurivanets.bottomsheets.sheets.model.Option
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
class MyCardModule {
    /*: BaseFragmentModule<MyCardFragment>() {
    @Provides
    @ViewModelInjection
    fun provideMyCardVM(
        fragment: MyCardFragment,
        viewModelProvider: InjectionViewModelProvider<MyCardVM>
    ): MyCardVM = viewModelProvider.get(fragment, MyCardVM::class)

    @Provides
    @FragmentScope
    fun provideMyCardState(): IMyCard.State = MyCardState()

    @Provides
    @FragmentScope
    fun provideRecyclerViewExpandableItemManager() =
        RecyclerViewExpandableItemManager(null)

    @Provides
    @FragmentScope
    fun provideHomeTransactionAdapter(expandableItemManager: RecyclerViewExpandableItemManager) =
        HomeTransactionAdapter(emptyMap(), expandableItemManager)

    @Provides
    @FragmentScope
    fun provideWrappedAdapter(
        adapter: HomeTransactionAdapter,
        mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    ): RecyclerView.Adapter<*> = mRecyclerViewExpandableItemManager.createWrappedAdapter(adapter)
*/

    fun provideOptionsList(context: MyCardFragment): ArrayList<Option> {
        return ArrayList<Option>().apply {
            add(
                Option().setId(R.id.change_pin.toLong())
                    .setTitle(
                        getString(
                            context.requireContext(),
                            Strings.screen_household_my_card_screen_menu_change_pin_text
                        )
                    )
            )
            add(
                Option().setId(R.id.freeze_card.toLong())
                    .setTitle(
                        getString(
                            context.requireContext(),
                            Strings.screen_household_my_card_screen_menu_freeze_card_text
                        )
                    )
            )
            add(
                Option().setId(R.id.view_statement.toLong())
                    .setTitle(
                        getString(
                            context.requireContext(),
                            Strings.screen_household_my_card_screen_menu_view_statement_text
                        )
                    )
            )
            add(
                Option().setId(R.id.report_lost_card.toLong())
                    .setTitle(
                        getString(
                            context.requireContext(),
                            Strings.screen_household_my_card_screen_menu_report_lost_card_text
                        )
                    )
            )
            add(
                Option().setId(R.id.cancel.toLong())
                    .setTitle(
                        getString(
                            context.requireContext(),
                            Strings.screen_household_my_card_screen_menu_cancel_text
                        )
                    )
            )
        }
    }
}
