package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.customers.responsedtos.SubAccounts
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.managers.MyUserManager
import javax.inject.Inject

class SubAccountCardVM @Inject constructor(override val state: ISubAccountCard.State) :
    BaseRecyclerAdapterVM<SubAccount, ISubAccountCard.State>(), ISubAccountCard.ViewModel {

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        val accounts = SubAccounts()
        accounts.account?.add(0, SubAccount(accountType = AccountType.B2C_ACCOUNT.name))
        accounts.account?.add(1, SubAccount(accountType = null))
        accounts.account?.let { addData(it) }
    }
}