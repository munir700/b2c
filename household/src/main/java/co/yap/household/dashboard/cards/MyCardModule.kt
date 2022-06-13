package co.yap.household.dashboard.cards

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.yap.household.R
import co.yap.household.dashboard.home.HomeTransactionAdapter
import co.yap.translation.Strings
import co.yap.translation.Translator.getString
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import com.arthurivanets.bottomsheets.sheets.model.Option
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import java.util.*

@Module
@InstallIn(FragmentComponent::class)
class MyCardModule {

    /* @Provides
     @FragmentScoped
     fun provideRecyclerViewExpandableItemManager() =
         RecyclerViewExpandableItemManager(null)

      @Provides
     @FragmentScoped
     fun provideWrappedAdapter(
         adapter: HomeTransactionAdapter,
         mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
     ): RecyclerView.Adapter<*> = mRecyclerViewExpandableItemManager.createWrappedAdapter(adapter)
 */
    @Provides
    @FragmentScoped
    fun provideHomeTransactionAdapter(expandableItemManager: RecyclerViewExpandableItemManager) =
        HomeTransactionAdapter(emptyMap(), expandableItemManager)

    @Provides
    @FragmentScoped
    fun provideOptionsList(context: Fragment): ArrayList<Option> {
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
