package co.yap.household.dashboard.cards

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class MyCardVM @Inject constructor(override var state: IMyCard.State) :
    BaseRecyclerAdapterVM<TransactionModel, IMyCard.State>(), IMyCard.ViewModel {
    
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

}