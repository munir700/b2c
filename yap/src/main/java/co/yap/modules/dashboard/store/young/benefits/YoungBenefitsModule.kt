package co.yap.modules.dashboard.store.young.benefits

import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsAdapter
import co.yap.modules.dashboard.store.young.benefits.adapter.YoungBenefitsModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class YoungBenefitsModule {

    @Provides
    fun provideYoungBenefitsAdapter() = YoungBenefitsAdapter(getBenefitList(), null)

    fun getBenefitList(): MutableList<YoungBenefitsModel> {
          val benefits: MutableList<YoungBenefitsModel> = ArrayList<YoungBenefitsModel>().apply {
            add(YoungBenefitsModel("Send pocket money to their account"))
            add(YoungBenefitsModel("Monitor their spending habits"))
            add(YoungBenefitsModel("Set mission to your child to earn money"))
            add(YoungBenefitsModel("Create saving goals"))
        }
        return benefits
    }
}
