package co.yap.yapcore.helpers.livedata

import androidx.annotation.MainThread
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.SingleSingletonHolder
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import kotlin.coroutines.coroutineContext

class GetAccountInfoLiveData : LiveDataCallAdapter<AccountInfo?>() {
    private val repository: CustomersRepository = CustomersRepository
    var user: AccountInfo? = null
    var usersList: ArrayList<AccountInfo> = arrayListOf()

    override fun onActive() {
        super.onActive()
        launch {
            when (val response = repository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    usersList = response.data.data as ArrayList
                    user = getCurrentUser()
                    SessionManager.user = user
                    SessionManager.usersList?.value = usersList
                    SessionManager.setupDataSetForBlockedFeatures(SessionManager.card.value)
                    value = user
                }

                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    private fun getCurrentUser(): AccountInfo? {
        return (if (isExistingUser()) {
            user = getHouseholdUser()
            if (isOnBoarded()) {
                getYapUser()
            } else
                getHouseholdUser()
        } else {
            if (getYapUser() != null) getYapUser() else getHouseholdUser()
        })
    }

    fun isExistingUser(): Boolean {
        return getYapUser() != null && getHouseholdUser() != null
    }
    private fun getYapUser(): AccountInfo? {
        return usersList.firstOrNull { account -> account.accountType == AccountType.B2C_ACCOUNT.name }
    }

    private fun getHouseholdUser(): AccountInfo? {
        return usersList.firstOrNull { account -> account.accountType == AccountType.B2C_HOUSEHOLD.name }
    }

    /*
        isOnBoarded:  This method is used for Household user to check if it's on boarded or not
     */

    fun isOnBoarded(): Boolean {
        // TODO Verify login with IOS team
        if (user?.notificationStatuses != AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING.name &&
            user?.notificationStatuses != AccountStatus.INVITE_PENDING.name &&
            user?.notificationStatuses != AccountStatus.EMAIL_PENDING.name &&
            user?.notificationStatuses != AccountStatus.PASS_CODE_PENDING.name
        ) {
            return true
        }
        return false
    }


    override fun onInactive() {
        super.onInactive()
        value = null
        cancelAllJobs()
    }

    @MainThread
    companion object : SingleSingletonHolder<GetAccountInfoLiveData>(::GetAccountInfoLiveData)
}