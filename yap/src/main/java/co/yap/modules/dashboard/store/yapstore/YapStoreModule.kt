package co.yap.modules.dashboard.store.yapstore

import co.yap.R
import co.yap.networking.store.responsedtos.Store
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import co.yap.yapcore.enums.YAPThemes
import dagger.Module
import dagger.Provides

@Module
class YapStoreModule : BaseFragmentModule<YapStoreFragment>() {
    @Provides
    @ViewModelInjection
    fun provideYapStoreVM(
        fragment: YapStoreFragment,
        viewModelProvider: InjectionViewModelProvider<YapStoreVM>
    ) = viewModelProvider.get(fragment, YapStoreVM::class)

    @Provides
    @FragmentScope
    fun provideYapStoreState(): IYapStore.State = YapStoreState()

    @Provides
    @FragmentScope
    fun provideYapStoreAdapter() = YapStoreFragment.Adapter(getStoreList(), null)

    private fun getStoreList(): MutableList<Store> {
        // need api in future
        val list = mutableListOf<Store>()
        list.add(
            Store(
                R.id.youngStore,
                "YAP Young",
                "Open a bank account for your children and help empower them financially.",
                R.drawable.ic_store_young, R.drawable.ic_young_smile,true
            )
        )
        list.add(
            Store(
                R.id.houseHoldStore,
                "YAP Household",
                "Manage your household salaries digitally.",
                R.drawable.ic_store_household, R.drawable.ic_young_household,false
            )
        )
        return list
    }
}
