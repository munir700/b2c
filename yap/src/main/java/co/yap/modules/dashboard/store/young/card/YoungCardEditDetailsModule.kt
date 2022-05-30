package co.yap.modules.dashboard.store.young.card

import co.yap.networking.customers.responsedtos.HouseHoldCardsDesign
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class YoungCardEditDetailsModule { /*: BaseFragmentModule<YoungCardEditDetailsFragment>(){
    @Provides
    @ViewModelInjection
    fun provideCardEditDetailsVM(
        fragment: YoungCardEditDetailsFragment,
        viewModelProvider: InjectionViewModelProvider<YoungCardEditDetailsVM>
    ): YoungCardEditDetailsVM = viewModelProvider.get(fragment, YoungCardEditDetailsVM::class)

    @Provides
    @FragmentScope
    fun provideCardEditDetailsState(): IYoungCardEditDetails.State = YoungCardEditDetailsState()
*/
    @Provides
    fun provideCardEditDetailsAdapter() = YoungCardEditAdapter(getHouseHoldCards(), null)

    private fun getHouseHoldCards(): MutableList<HouseHoldCardsDesign> {
        var youngCard: MutableList<HouseHoldCardsDesign> = mutableListOf()
        youngCard.add(HouseHoldCardsDesign("","https://milestomemories.com/wp-content/uploads/2018/06/Venmo-card-Venmo.png","123","Testcolorcode","#673ab7","https://milestomemories.com/wp-content/uploads/2018/06/Venmo-card-Venmo.png","true","#F44774","32","dffkelsfl"))
        youngCard.add(HouseHoldCardsDesign("","https://milestomemories.com/wp-content/uploads/2018/06/Venmo-card-Venmo.png","344","Testcolorcode","#ffc430","https://milestomemories.com/wp-content/uploads/2018/06/Venmo-card-Venmo.png","true","#673ab7","32","dffkelsfl"))
        youngCard.add(HouseHoldCardsDesign("","https://milestomemories.com/wp-content/uploads/2018/06/Venmo-card-Venmo.png","445","Testcolorcode","#F44774","https://milestomemories.com/wp-content/uploads/2018/06/Venmo-card-Venmo.png","true","#ffc430","32","dffkelsfl"))
        return youngCard
    }
}