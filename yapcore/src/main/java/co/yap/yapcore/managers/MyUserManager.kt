package co.yap.yapcore.managers

import android.content.Context
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
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
    private val customerRepository: CustomersRepository = CustomersRepository
    private var usersList: List<AccountInfo?> = arrayListOf()
    var user: AccountInfo? = null
    var userAddress: Address? = null
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var cards: MutableLiveData<Card?> = MutableLiveData()
    var eidStatus: EIDStatus = EIDStatus.NOT_SET
    var onAccountInfoSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun updateCardBalance() {
        getAccountBalanceRequest()
    }

    fun getAccountInfo() {
        GlobalScope.launch {
            when (val response = customerRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    usersList = response.data.data as ArrayList
                    user = getCurrentUser()
                    onAccountInfoSuccess.postValue(true)
                }

                is RetroApiResponse.Error -> {
                }
            }
        }
    }

    private fun getYapUser(): AccountInfo? {
        return usersList.firstOrNull { account -> account?.accountType == AccountType.B2C_ACCOUNT.name }
    }

    private fun getHouseholdUser(): AccountInfo? {
        return usersList.firstOrNull { account -> account?.accountType == AccountType.B2C_HOUSEHOLD.name }
    }

    private fun getCurrentUser(): AccountInfo? {
        return (if (isExistingUser()) {
            if (AccountStatus.INVITATION_PENDING.name != getHouseholdUser()?.notificationStatuses || AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING.name != getHouseholdUser()?.notificationStatuses) {
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