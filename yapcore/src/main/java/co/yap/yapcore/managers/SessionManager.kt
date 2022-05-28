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
import co.yap.networking.customers.responsedtos.SystemConfigurationInfo
import co.yap.networking.customers.responsedtos.currency.CurrencyData
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.NotificationsRepository
import co.yap.networking.notification.requestdtos.FCMTokenRequest
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_IS_USER_LOGGED_IN
import co.yap.yapcore.constants.Constants.SYSTEM_CONFIGURATION
import co.yap.yapcore.enums.*
import co.yap.yapcore.firebase.getFCMToken
import co.yap.yapcore.helpers.AuthUtils
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.getBlockedFeaturesList
import co.yap.yapcore.helpers.extentions.getUserAccessRestrictions
import co.yap.yapcore.helpers.extentions.listToJson
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
    var userLiveData: MutableLiveData<AccountInfo?> = MutableLiveData()
    var userAddress: Address? = null

    //    @Deprecated("must use co.yap.yapcore.helpers.livedata.GetAccountBalanceLiveData")
    var cardBalance: MutableLiveData<CardBalance> = MutableLiveData()
    var card: MutableLiveData<Card?> = MutableLiveData()
    var eidStatus: EIDStatus = EIDStatus.NOT_SET
    var helpPhoneNumber: String = "+971600551214"
    var onAccountInfoSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var tempLoginState: MutableLiveData<Boolean> = MutableLiveData()
    private val currencies: MutableLiveData<ArrayList<CurrencyData>> = MutableLiveData()
    private val countries: MutableLiveData<ArrayList<Country>> = MutableLiveData()
    var isRemembered: MutableLiveData<Boolean> = MutableLiveData(true)
    private const val DEFAULT_CURRENCY: String = "AED"
    var isFounder: MutableLiveData<Boolean?> = MutableLiveData(false)
    var deepLinkFlowId: MutableLiveData<String?> = MutableLiveData(null)
    var systemConfiguration: MutableLiveData<MutableMap<String?, SystemConfigurationInfo>> =
        MutableLiveData()
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
                    currencies.postValue(apiResponse.data.curriencies ?: arrayListOf())
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
                    setupDataSetForBlockedFeatures(card = card.value)
                    onAccountInfoSuccess.postValue(true)
                    success.invoke()
                }

                is RetroApiResponse.Error -> {
                    onAccountInfoSuccess.postValue(false)
                }
            }
        }
    }

    fun getSystemConfigurationInfo(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            when (val response = customerRepository.getSystemConfigurations()) {
                is RetroApiResponse.Success -> {
                    response.data.data.let { list ->
                        val listArray = ArrayList(list)
                        SharedPreferenceManager.getInstance(context).save(
                            SYSTEM_CONFIGURATION,
                            Gson().toJson(listArray)
                        )
                        systemConfiguration.postValue(
                            convertSystemConfigurationInfoIntoMap(
                                SharedPreferenceManager.getInstance(context)
                                    .getValueString(SYSTEM_CONFIGURATION)
                            )
                        )
                    }
                }
                is RetroApiResponse.Error -> {
                    getSystemConfigurationInfo(context)
                }
            }
        }
    }

    fun setupDataSetForBlockedFeatures(card: Card?) {
        user?.getUserAccessRestrictions(card = card) {
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
//            user = getHouseholdUser()
            if (isOnBoarded(getHouseholdUser())) {
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
    fun isOnBoarded(user : AccountInfo?): Boolean {
        // TODO Verify login with IOS team
        if (user?.notificationStatuses != AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING.name &&
            user?.notificationStatuses != AccountStatus.INVITE_PENDING.name &&
            user?.notificationStatuses != AccountStatus.EMAIL_PENDING.name &&
            user?.notificationStatuses != AccountStatus.PASS_CODE_PENDING.name && !user?.fssRequestRefNo.isNullOrEmpty()
        ) {
            return true
        }
        return false
    }

    fun getDebitCard(success: (card: Card?) -> Unit = {}) {
        GlobalScope.launch(Dispatchers.Main) {
            val cardType = when (user?.accountType) {
                AccountType.B2C_ACCOUNT.name -> CardType.DEBIT.type
                AccountType.B2C_HOUSEHOLD.name -> CardType.PREPAID.type
                else -> ""
            }

            when (val response = repository.getDebitCards(cardType)) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNullOrEmpty()) {
                        success.invoke(null)
                    } else {
                        if (cardType == CardType.DEBIT.type) {
                            getDebitFromList(response.data.data)?.let { debitCard ->
                                updateCard(debitCard)
                                success.invoke(debitCard)
                            } ?: success.invoke(null)
                        } else if (cardType == CardType.PREPAID.type) {
                            getPrepaidFromList(response.data.data)?.let { debitCard ->
                                updateCard(debitCard)
                                success.invoke(debitCard)
                            } ?: success.invoke(null)
                        }

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

    fun getPrepaidFromList(it: ArrayList<Card>?): Card? {
        return it?.firstOrNull {
            it.cardType == CardType.PREPAID.type
        }
    }

    fun getCardSerialNumber(): String {
        card.value?.let {
            return when (it.cardType) {
                CardType.DEBIT.type, CardType.PREPAID.type -> it.cardSerialNumber
                else -> ""
            }
        }
        return ""
    }

    fun getPrimaryCard(): Card? {
        card.value?.let {
            return when (it.cardType) {
                CardType.DEBIT.type, CardType.PREPAID.type -> return it
                else -> null
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

    fun sendFcmTokenToServer(deviceId: String?, success: () -> Unit = {}) {
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

    fun getAppCountries(
        context: Context,
        completionHandler: ((result: ArrayList<co.yap.networking.customers.responsedtos.sendmoney.Country>?, msg: String?) -> Unit)? = null
    ) {
        GlobalScope.launch {
            when (val response = customerRepository.getAppCountries()) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        val list = ArrayList(it)
                        SharedPreferenceManager.getInstance(context)
                            .save(
                                Constants.KEY_COUNTRIES_LIST,
                                list.listToJson<co.yap.networking.customers.responsedtos.sendmoney.Country>()
                                    ?: ""
                            )
                        completionHandler?.invoke(list, null)

                    }
                }
                is RetroApiResponse.Error -> {
                    completionHandler?.invoke(null, response.error.message)
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

    fun updateCard(userCard: Card?) {
        card.postValue(userCard)
    }

}

fun Context.saveUserDetails(mobile: String?, countryCode: String?, isRemember: Boolean?) {
    SharedPreferenceManager.getInstance(this).save(Constants.KEY_IS_REMEMBER, isRemember ?: true)
    SharedPreferenceManager.getInstance(this)
        .save(Constants.KEY_MOBILE_NO, mobile ?: "")
    SharedPreferenceManager.getInstance(this)
        .save(Constants.KEY_COUNTRY_CODE, countryCode ?: "")
}

fun Context?.isUserLogin() = this?.let {
    SharedPreferenceManager.getInstance(it)
        .getValueBoolien(Constants.KEY_IS_USER_LOGGED_IN, false) && SessionManager.user != null
}

fun convertSystemConfigurationInfoIntoMap(jsonString: String?): MutableMap<String?, SystemConfigurationInfo> =
    if (jsonString.isNullOrBlank().not()) {
        try {
            val type =
                object : TypeToken<java.util.ArrayList<SystemConfigurationInfo?>?>() {}.type
            val list: ArrayList<SystemConfigurationInfo> = Gson().fromJson(
                jsonString,
                type
            )
            list.associateBy({ it.key }, { it }).toMutableMap()
        } catch (e: Exception) {
            mutableMapOf()
        }
    } else mutableMapOf()

fun AccountInfo?.setCrashlyticsUser() {
    this?.let { accountInfo ->
        Firebase.analytics.setUserId(accountInfo.uuid ?: "")
        Firebase.analytics.setUserProperty(
            "customerId",
            accountInfo.currentCustomer.customerId ?: ""
        )
        val crashlytics = Firebase.crashlytics
        crashlytics.setCustomKeys {
            key("customerId", accountInfo.currentCustomer.customerId ?: "")
            key("Email", accountInfo.currentCustomer.email ?: "")
            key("CustomerUuid", accountInfo.currentCustomer.uuid ?: "beforeLogin")
            key("AccountUuid", accountInfo.uuid ?: "beforeLogin")
            key(KEY_IS_USER_LOGGED_IN,true)
        }
    }
}
