package co.yap.modules.dashboard.store.young.landing.benefits

import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsModel
import co.yap.yapcore.dagger.di.InjectionViewModelProvider
import co.yap.yapcore.dagger.di.module.fragment.BaseFragmentModule
import co.yap.yapcore.dagger.di.qualifiers.FragmentScope
import co.yap.yapcore.dagger.di.qualifiers.ViewModelInjection
import dagger.Module
import dagger.Provides

@Module
class YoungBenefitsModule : BaseFragmentModule<YoungBenefitsFragment>(){
    @Provides
    @ViewModelInjection
    fun provideYoungBenefitsVM(
        fragment: YoungBenefitsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungBenefitsVM>
    ): YoungBenefitsVM = viewModelProvider.get(fragment, YoungBenefitsVM::class)

    @Provides
    @FragmentScope
    fun provideYoungBenefitsState(): IYoungBenefits.State = YoungBenefitsState()

    @Provides
    @FragmentScope
    fun provideYoungBenefitsAdapter() = YoungBenefitsAdapter(getBenefitList(), null)

   private fun getBenefitList(): MutableList<YoungBenefitsModel> {
        var benefits: MutableList<YoungBenefitsModel> = ArrayList()
        benefits.add(YoungBenefitsModel("Send pocket money to their account"))
        benefits.add(YoungBenefitsModel("Monitor their spending habits"))
        benefits.add(YoungBenefitsModel("Set mission to your child to earn money"))
        benefits.add(YoungBenefitsModel("Create saving goals"))
        return benefits
    }
}
