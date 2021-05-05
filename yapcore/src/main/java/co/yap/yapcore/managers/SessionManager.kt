package co.yap.yapcore.managers

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.yap.app.YAPApplication
import co.yap.countryutils.country.Country
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.cards.CardsRepository
import co.yap.networking.cards.responsedtos.Address
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardBalance
import co.yap.networking.customers.CustomersApi
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.customers.responsedtos.currency.CurrencyData
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.NotificationsRepository
import co.yap.networking.notification.requestdtos.FCMTokenRequest
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.*
import co.yap.yapcore.firebase.getFCMToken
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getBlockedFeaturesList
import co.yap.yapcore.helpers.extentions.getUserAccessRestrictions
import com.liveperson.infra.auth.LPAuthenticationParams
import com.liveperson.messaging.sdk.api.LivePerson
import com.liveperson.messaging.sdk.api.callbacks.LogoutLivePersonCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

object SessionManager : IRepositoryHolder<CardsRepository> {

    override val repository: CardsRepository = CardsRepository
    private val customerRepository: CustomersApi = CustomersRepository
    private val authRepository: AuthRepository = AuthRepository
    var usersList: MutableLiveData<ArrayList<AccountInfo>>? = MutableLiveData()
    var user: AccountInfo? = null
        set(value) {
            field = value
            userLiveData.postValue(value)
        }
    var userLiveData: MutableLiveData<AccountInfo> = MutableLiveData<AccountInfo>()
    var switchProfile: SingleLiveEvent<Boolean> = SingleLiveEvent()
    var userAddress: Address? = null

    //    @Deprecated("must use co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData")
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var card: MutableLiveData<Card?> = MutableLiveData()
    var eidStatus: EIDStatus = EIDStatus.NOT_SET
    var helpPhoneNumber: String = ""
    var onAccountInfoSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private val currencies: MutableLiveData<ArrayList<CurrencyData>> = MutableLiveData()
    private val countries: MutableLiveData<ArrayList<Country>> = MutableLiveData()
    var isRemembered: MutableLiveData<Boolean> = MutableLiveData(true)
    private const val DEFAULT_CURRENCY: String = "AED"
    var isFounder: MutableLiveData<Boolean> = MutableLiveData(false)
    var deepLinkFlowId: MutableLiveData<String?> = MutableLiveData(null)
    val homeCountry2Digit: String
        get() {
            return if (user?.currentCustomer?.homeCountry?.count() == 3) countries.value?.find { it.isoCountryCode3Digit == user?.currentCustomer?.homeCountry }?.isoCountryCode2Digit
                    ?: "AE"
            else
                user?.currentCustomer?.homeCountry ?: "AE"
        }
    private val viewModelBGScope =
            BaseViewModel.CloseableCoroutineScope(Job() + Dispatchers.IO)

    fun getCurrenciesFromServer(response: (success: Boolean, currencies: ArrayList<CurrencyData>) -> Unit) {
        // feature disable for later enabling as RAK permit.
        GlobalScope.launch(Dispatchers.IO) {
            when (val apiResponse = customerRepository.getAllCurrenciesConfigs()) {
                is RetroApiResponse.Success -> {
                    currencies.postValue(apiResponse.data.curriencies)
                    response.invoke(true, apiResponse.data.curriencies ?: arrayListOf())
                }

                is RetroApiResponse.Error -> {
                    response.invoke(false, arrayListOf())
                }
            }
        }
    }

    fun getCountriesFromServer(response: (success: Boolean, countries: ArrayList<Country>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            when (val apiResponse = customerRepository.getCountries()) {
                is RetroApiResponse.Success -> {
                    countries.postValue(
                            Utils.parseCountryList(
                                    apiResponse.data.data,
                                    addOIndex = false
                            )
                    )
                    response.invoke(true, countries.value ?: arrayListOf())
                }

                is RetroApiResponse.Error -> {
                    response.invoke(false, arrayListOf())
                }
            }
        }
    }

    fun getCurrencies(): ArrayList<CurrencyData> {
        return currencies.value ?: arrayListOf()
    }

    fun getCountries(): ArrayList<Country> {
        return countries.value ?: arrayListOf()
    }

    fun getDefaultCurrencyDecimals(): Int = 2

    @Deprecated("Use GetAccountBalanceLiveData instead")
    fun updateCardBalance(success: () -> Unit) {
        getAccountBalanceRequest {
            success()
        }
    }

    @Deprecated("Not used anymore")
    fun getAccountInfo(success: () -> Unit = {}) {
        GlobalScope.launch {
            when (val response = customerRepository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    usersList?.postValue(response.data.data as ArrayList)
//                    usersList?.value = response.data.data as ArrayList
                    user = getCurrentUser()
                    isFounder.postValue(user?.currentCustomer?.founder)
                    setupDataSetForBlockedFeatures()
                    onAccountInfoSuccess.postValue(true)
                    success.invoke()
                }

                is RetroApiResponse.Error -> {
                    onAccountInfoSuccess.postValue(false)
                }
            }
        }
    }

    fun setupDataSetForBlockedFeatures() {
        user?.getUserAccessRestrictions {
            val featuresList = arrayListOf<FeatureSet>()
            it.forEach { userAccessRestriction ->
                featuresList.addAll(user.getBlockedFeaturesList(userAccessRestriction))
            }
            FeatureProvisioning.configure(
                    featuresList,
                    it
            )
        }
    }

    private fun getYapUser(): AccountInfo? {
        return usersList?.value?.firstOrNull { account -> account.accountType == AccountType.B2C_ACCOUNT.name }
    }

    private fun getHouseholdUser(): AccountInfo? {
        return usersList?.value?.firstOrNull { account -> account.accountType == AccountType.B2C_HOUSEHOLD.name }
    }

    fun getCurrentUser(): AccountInfo? {
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

    private fun getAccountBalanceRequest(success: () -> Unit) {
        GlobalScope.launch {
            when (val response = repository.getAccountBalanceRequest()) {
                is RetroApiResponse.Success -> {
                    cardBalance.postValue(CardBalance(availableBalance = response.data.data?.availableBalance.toString()))
                    success()
                }
                is RetroApiResponse.Error -> {

                }
            }
        }
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

    fun getDebitCard(success: (card: Card?) -> Unit = {}) {
        GlobalScope.launch(Dispatchers.Main) {
            when (val response = repository.getDebitCards("DEBIT")) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) {
                        success.invoke(null)
                    } else {
                        getDebitFromList(response.data.data)?.let { debitCard ->
                            card.postValue(debitCard)
                            success.invoke(debitCard)
                        } ?: success.invoke(null)
                    }
                }
                is RetroApiResponse.Error -> {
                    success.invoke(null)
                }
            }
        }
    }

    fun getDebitFromList(it: ArrayList<Card>?): Card? {
        return it?.firstOrNull {
            it.cardType == CardType.DEBIT.type
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

    fun expireUserSession() {
        user = null
        cardBalance.value = CardBalance()
        card = MutableLiveData()
        userAddress = null
        YAPApplication.clearFilters()
        cancelAllJobs()
    }

    private fun cancelAllJobs() {
        viewModelBGScope.close()
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
    }

    fun getDefaultCurrency() = DEFAULT_CURRENCY

    fun sendFcmTokenToServer(context: Context, success: () -> Unit = {}) {
        val sharedPreferenceManager = SharedPreferenceManager.getInstance(context)
        val deviceId: String? = sharedPreferenceManager.getValueString(Constants.KEY_APP_UUID)

        getFCMToken() {
            it?.let { token ->
                GlobalScope.launch {
                    when (val response = NotificationsRepository.sendFcmTokenToServer(
                            FCMTokenRequest(
                                    token = it,
                                    deviceId = deviceId
                            )
                    )) {
                        is RetroApiResponse.Success -> {
                            success.invoke()
                        }
                        is RetroApiResponse.Error -> {
                            Log.d("", "")
                        }
                    }
                }
            }

        }
    }

    fun shouldGoToHousehold(): Boolean {
        val yapUser = getYapUser()
        val householdUser = getHouseholdUser()
        if ((yapUser != null && householdUser != null) || (yapUser == null && householdUser != null)) {
            return true
        }
        return false
    }
}

fun Context?.isUserLogin() = this?.let {
    SharedPreferenceManager.getInstance(it)
        .getValueBoolien(Constants.KEY_IS_USER_LOGGED_IN, false) && SessionManager.user != null
}
