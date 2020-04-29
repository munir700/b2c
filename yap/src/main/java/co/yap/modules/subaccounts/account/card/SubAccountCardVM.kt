package co.yap.modules.subaccounts.account.card

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.SubAccount
import co.yap.networking.customers.responsedtos.SubAccounts
import co.yap.networking.models.RetroApiResponse
import co.yap.widgets.State
import co.yap.widgets.advrecyclerview.adapter.SimpleWrapperAdapter
import co.yap.yapcore.dagger.base.viewmodel.BaseRecyclerAdapterVM
import co.yap.yapcore.enums.AccountType
import javax.inject.Inject

class SubAccountCardVM @Inject constructor(override val state: ISubAccountCard.State) :
    BaseRecyclerAdapterVM<SubAccount, ISubAccountCard.State>(), ISubAccountCard.ViewModel {
    private val repository: CustomersRepository = CustomersRepository
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
        getSubAccount()
        getEmptyAccount()?.let { addData(it) }
    }

    private fun getEmptyAccount(): MutableList<SubAccount>? {
        val accounts = SubAccounts()
        accounts.account?.add(0, SubAccount(accountType = AccountType.B2C_ACCOUNT.name))
        accounts.account?.add(
            1,
            SubAccount(accountType = null, cardStatus = "Add new card")
        )
        return accounts.account
    }


    override fun getSubAccount() {
        launch {
            publishState(State.loading(null))
            when (val response = repository.getSubAccounts()) {
                is RetroApiResponse.Success -> {
                    response.data.account?.let {
                        it.add(0, SubAccount(accountType = AccountType.B2C_ACCOUNT.name))
                        it.add(
                            SubAccount(
                                accountType = null,
                                cardStatus = "Add new card"
                            )
                        )
                        setData(it)
                    }
                    publishState(State.success(null))
                }
                is RetroApiResponse.Error -> {
                    getEmptyAccount()?.let { addData(it) }
                    publishState(State.success(null))
                }
            }
        }
    }

    override fun resendRequestToHouseHoldUser(account: SubAccount) {
        launch {
            when (val response = repository.resendRequestToHouseHoldUser(account.accountUuid)) {
                is RetroApiResponse.Success -> {
                    state.toast = "Request Resend!"
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }

    override fun RemoveRefundHouseHoldUser(account: SubAccount) {
        launch {
            when (val response = repository.RemoveRefundHouseHoldUser(account.accountUuid)) {
                is RetroApiResponse.Success -> {
                    removeItem(account)
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        getSubAccount()
    }
}