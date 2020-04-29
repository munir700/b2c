package co.yap.yapcore.managers

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.enums.CardType
import co.yap.yapcore.enums.EIDStatus
import co.yap.yapcore.helpers.AuthUtils
import com.liveperson.infra.LPAuthenticationParams
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.callbacks.LogoutLivePersonCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object MyUserManager : IRepositoryHolder<CardsRepository> {

    override val repository: CardsRepository = CardsRepository
    private val customersRepository: CustomersApi = CustomersRepository
    private val authRepository: AuthRepository = AuthRepository

    var usersList: ArrayList<AccountInfo> = arrayListOf()
    var user: AccountInfo? = null
        set(value) {
            field = value
            userLiveData.postValue(value)
        }
    var userLiveData: MutableLiveData<AccountInfo> = MutableLiveData<AccountInfo>()

    var switchProfile: SingleLiveEvent<Boolean> = SingleLiveEvent()

    //    var users: ArrayList<AccountInfo> = ArrayList<AccountInfo>()
    var userAddress: Address? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var card: MutableLiveData<Card?> = MutableLiveData()
    var eidStatus: EIDStatus = EIDStatus.NOT_SET
    var onAccountInfoSuccess: SingleLiveEvent<Boolean> = SingleLiveEvent()

    @Deprecated("Use GetAccountBalanceLiveData instead")
    fun updateCardBalance() {
        getAccountBalanceRequest()
    }

    @Deprecated("Not used anymore")
    fun getAccountInfo() {
        GlobalScope.launch {
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    usersList = response.data.data as ArrayList
                    user = getCurrentUser()
                    onAccountInfoSuccess.postValue(true)
                }

                is RetroApiResponse.Error -> {
                    onAccountInfoSuccess.postValue(false)
                }
            }
        }
    }

    private fun getYapUser(): AccountInfo? {
        return usersList.firstOrNull { account -> account.accountType == AccountType.B2C_ACCOUNT.name }
    }

    private fun getHouseholdUser(): AccountInfo? {
        return usersList.firstOrNull { account -> account.accountType == AccountType.B2C_HOUSEHOLD.name }
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

    private fun getAccountBalanceRequest() {

        GlobalScope.launch {
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    cardBalance.postValue(CardBalance(availableBalance = response.data.data?.availableBalance.toString()))
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
    }

//    fun getAccountInfo() {
//        GlobalScope.launch {
//            when (val response = customersRepository.getAccountInfo()) {
//                is RetroApiResponse.Success -> {
//                    if (!response.data.data.isNullOrEmpty()) {
//
//                        usersList = response.data.data as ArrayList<AccountInfo>
//                        setUser(usersList)
//
//                        onAccountInfoSuccess.postValue(true)
//                    }
//                }
//                is RetroApiResponse.Error -> {
//                    onAccountInfoSuccess.postValue(false)
//                }
//            }
//        }
//    }

    private fun switchUser() {
        GlobalScope.launch {
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
                        usersList = response.data.data as ArrayList<AccountInfo>
                        user = getCurrentUser()
                        //setUser(usersList)

                        // Reverse Users so that household remain on top for household dashboard menu
                        usersList.reverse()

                        switchProfile.postValue(true)
                    }
                }
                is RetroApiResponse.Error -> {
                    switchProfile.postValue(false)
                }
            }
        }
    }

    private fun setUser(users: ArrayList<AccountInfo>) {
        val yapUser = getYapUser()
        val householdUser = getHouseholdUser()

        if (householdUser != null && yapUser != null) {
            user = householdUser
        } else if (householdUser != null) {
            user = householdUser
        } else if (yapUser != null) {
            user = yapUser
        }
        userLiveData.postValue(user)

    }

    fun shouldGoToHousehold(): Boolean {
        val yapUser = getYapUser()
        val householdUser = getHouseholdUser()
        if ((yapUser != null && householdUser != null) || (yapUser == null && householdUser != null)) {
            return true
        }
        return false
    }

    fun isDefaultUserYap(): Boolean {
        val yapUser = getYapUser()
        val householdUser = getHouseholdUser()

        if ((yapUser != null && householdUser != null)) {
            if (yapUser.defaultProfile == true) {
                return true
            } else if (householdUser.defaultProfile == true) {
                return false
            }

        }
        return false
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

    private fun getYapUserAccount(data: ArrayList<AccountInfo>): AccountInfo? {
        return data.find { obj1 -> obj1.accountType == AccountType.B2C_ACCOUNT.name }
    }

    private fun getHouseholdUserAccount(data: ArrayList<AccountInfo>): AccountInfo? {
        return data.find { obj1 -> obj1.accountType == AccountType.B2C_HOUSEHOLD.name }
    }

    fun switchProfile() {
        switchProfile(user?.uuid)
    }

    fun switchProfile(uuid: String?) {
        GlobalScope.launch {
            when (val response = uuid?.let { authRepository.switchProfile(it) }) {
                is RetroApiResponse.Success -> {
                    // call Account Info API
                    switchUser()
                }
                is RetroApiResponse.Error -> {
                    response.error.message
                    switchProfile.postValue(false)
                }
            }
        }
    }

    fun getCardSerialNumber(): String {
        card.value?.let {
            if (it.cardType == CardType.DEBIT.type) {
                return it.cardSerialNumber
            }
        }
        return ""
    }

    fun getPrimaryCard(): Card? {
        card.value?.let {
            if (it.cardType == CardType.DEBIT.type) {
                return it
            }
        }
        return null
    }

    private fun expireUserSession() {
        user = null
    }

    fun doLogout(context: Context, isOnPassCode: Boolean = false) {
        AuthUtils.navigateToHardLogin(context, isOnPassCode)
        expireUserSession()
        LivePerson.logOut(context, "", "", object : LogoutLivePersonCallback {
            override fun onLogoutSucceed() {
                val authParams = LPAuthenticationParams()
                authParams.hostAppJWT = ""
            }

            override fun onLogoutFailed() {

            }
        })
        cardBalance.value = CardBalance()
        card = MutableLiveData()
        userAddress = null
        YAPApplication.clearFilters()
    }
}