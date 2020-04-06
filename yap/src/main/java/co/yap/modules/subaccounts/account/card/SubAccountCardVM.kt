package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.modules.subaccounts.account.card.ISubAccountCard
import co.yap.networking.models.ApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import com.google.gson.Gson
import javax.inject.Inject

class SubAccountCardVM @Inject constructor(override val state: ISubAccountCard.State) :
    BaseRecyclerAdapterVM<ApiResponse, ISubAccountCard.State>(), ISubAccountCard.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        addData(MutableList(20){ApiResponse()})
    }
}