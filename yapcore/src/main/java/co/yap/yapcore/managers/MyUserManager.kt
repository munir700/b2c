package co.yap.yapcore.managers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
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
    private val customersRepository: CustomersRepository = CustomersRepository
    private val authRepository: AuthRepository = AuthRepository

    var user: AccountInfo? = null
    var userLiveData: MutableLiveData<AccountInfo> = MutableLiveData<AccountInfo>()

    var isUserAccountInfo: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var switchProfile: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var users: ArrayList<AccountInfo> = ArrayList<AccountInfo>()
    var userAddress: Address? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var cards: MutableLiveData<Card> = MutableLiveData()
    var eidStatus: EIDStatus = EIDStatus.NOT_SET

    fun updateCardBalance() {
        getAccountBalanceRequest()
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

    fun getAccountInfo() {
        GlobalScope.launch {
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {

                        users = response.data.data as ArrayList<AccountInfo>
                        setUser(users)

                        isUserAccountInfo.postValue(true)
                    }
                }
                is RetroApiResponse.Error -> {
                    isUserAccountInfo.postValue(false)
                }
            }
        }
    }

    private fun switchUser(){
        GlobalScope.launch {
            when (val response = customersRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (!response.data.data.isNullOrEmpty()) {
                        users = response.data.data as ArrayList<AccountInfo>
                        setUser(users)

                        // Reverse Users so that household remain on top for household dashboard menu
                         users.reverse()

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
        val yapUser = getYapUserAccount(this.users)
        val householdUser = getHouseholdUserAccount(this.users)

        if (householdUser != null && yapUser != null) {
            user = householdUser
        }
        else if (householdUser != null) {
            user = householdUser
        } else if (yapUser != null) {
            user = yapUser
        }
        userLiveData.postValue(user)

    }

    fun shouldGoToHousehold(): Boolean {
        val yapUser = getYapUserAccount(users)
        val householdUser = getHouseholdUserAccount(users)
        if ((yapUser != null && householdUser != null) || (yapUser == null && householdUser != null)) {
            return true
        }
        return false
    }

    fun isDefaultUserYap(): Boolean {
        val yapUser = users.find { obj1 -> obj1.accountType == AccountType.B2C_ACCOUNT.name }
        val householdUser =
            users.find { obj1 -> obj1.accountType == AccountType.B2C_HOUSEHOLD.name }

        if ((yapUser != null && householdUser != null)) {
            if(yapUser.defaultProfile == true){
                return true
            }else if(householdUser.defaultProfile == true){
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
            user?.notificationStatuses != AccountStatus.PASS_CODE_PENDING.name) {
            return true
        }
        return false
    }

    private fun getYapUserAccount(data: java.util.ArrayList<AccountInfo>): AccountInfo? {
        return data.find { obj1 -> obj1.accountType == AccountType.B2C_ACCOUNT.name }
    }

    private fun getHouseholdUserAccount(data: java.util.ArrayList<AccountInfo>): AccountInfo? {
        return data.find { obj1 -> obj1.accountType == AccountType.B2C_HOUSEHOLD.name }
    }

    fun switchProfile(){
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
        cards.value?.let {
            if (it.cardType == CardType.DEBIT.type) {
                return it.cardSerialNumber
            }
        }
        return ""
    }

    fun getPrimaryCard(): Card? {
        cards.value?.let {
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
        cards = MutableLiveData()
        userAddress = null
        YAPApplication.clearFilters()
    }
}