package co.yap.modules.dashboard.store.young.confirmrelationship

import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungConfirmRelationshipModule{}/*: BaseFragmentModule<YoungConfirmRelationshipFragment>() {
    @Provides
    @ViewModelInjection
    fun provideYoungConfirmRelationshipVM(
        fragment: YoungConfirmRelationshipFragment,
        viewModelProvider: InjectionViewModelProvider<YoungConfirmRelationshipVM>
    ): YoungConfirmRelationshipVM = viewModelProvider.get(fragment, YoungConfirmRelationshipVM::class)

    @Provides
    @FragmentScope
    fun provideContactDetailsState(): IYoungConfirmRelationship.State = YoungConfirmRelationshipState()
}*/
