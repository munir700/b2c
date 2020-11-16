package co.yap.sendmoney.home.viewmodels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.Y2YBeneficiariesResponse
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.networking.customers.responsedtos.sendmoney.GetAllBeneficiaryResponse
import co.yap.networking.customers.responsedtos.sendmoney.IBeneficiary
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.sendmoney.home.adapters.AllBeneficiariesAdapter
import co.yap.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.sendmoney.home.states.SendMoneyHomeState
import co.yap.sendmoney.viewmodels.SendMoneyBaseViewModel
import co.yap.translation.Strings
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.AlertType
import co.yap.yapcore.enums.SendMoneyBeneficiaryType
import co.yap.yapcore.enums.SendMoneyTransferType
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.parseRecentItems
import co.yap.yapcore.managers.SessionManager
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import kotlin.math.ceil


class SendMoneyHomeScreenViewModel(application: Application) :
    SendMoneyBaseViewModel<ISendMoneyHome.State>(application), ISendMoneyHome.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: SendMoneyHomeState = SendMoneyHomeState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override var pagingState: MutableLiveData<PagingState> = MutableLiveData()
    override val allBeneficiariesLiveData: MutableLiveData<List<Beneficiary>> = MutableLiveData()
    override var onDeleteSuccess: MutableLiveData<Int> = MutableLiveData()
    override var recentTransferData: MutableLiveData<List<Beneficiary>> = MutableLiveData()
    override val searchQuery: MutableLiveData<String> = MutableLiveData()
    override val isSearching: MutableLiveData<Boolean> = MutableLiveData()
    override var phoneContactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()
    override var recentsAdapter: CoreRecentTransferAdapter = CoreRecentTransferAdapter(
        context,
        mutableListOf()
    )
    override var beneficiariesAdapter: AllBeneficiariesAdapter = AllBeneficiariesAdapter(
        mutableListOf()
    )

    override fun listIsEmpty(): Boolean {
        return phoneContactLiveData.value?.isEmpty() ?: true
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        SessionManager.getCurrenciesFromServer { _, _ -> }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_send_money_display_text_title))
    }


    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

    override fun requestAllBeneficiaries(sendMoneyType: String) {
        if (sendMoneyType == SendMoneyTransferType.ALL_Y2Y_SM.name) {
            getY2YAndSMBeneficiaries {
                beneficiariesAdapter.setList(it)
            }
        } else {
            launch {
                state.loading = true
                when (val response = repository.getAllBeneficiaries()) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        val filteredList = getBeneficiariesOfType(sendMoneyType, response.data.data)
                        filteredList.parseRecentItems()

                        allBeneficiariesLiveData.value = filteredList
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                    }
                }
            }
        }
    }

    override fun requestRecentBeneficiaries(sendMoneyType: String) {
        launch {
            when (val response = repository.getRecentBeneficiaries()) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    val filteredList = getBeneficiariesOfType(sendMoneyType, response.data.data)
                    if (filteredList.isNullOrEmpty())
                        state.isNoRecentBeneficiary.set(true)
                    else
                        state.isNoRecentBeneficiary.set(false)

                    filteredList.parseRecentItems()
                    recentsAdapter.setList(filteredList)
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    private fun getBeneficiariesOfType(type: String, list: List<Beneficiary>): List<Beneficiary> {
        return when (type) {
            SendMoneyTransferType.HOME_COUNTRY.name -> {
                list.filter { it.country == SessionManager.user?.currentCustomer?.homeCountry }
            }
            SendMoneyTransferType.INTERNATIONAL.name -> {
                list.filter { (it.beneficiaryType == SendMoneyBeneficiaryType.RMT.type || it.beneficiaryType == SendMoneyBeneficiaryType.SWIFT.type) && it.country != SessionManager.user?.currentCustomer?.homeCountry }
            }
            SendMoneyTransferType.LOCAL.name -> {
                list.filter { it.beneficiaryType == SendMoneyBeneficiaryType.UAEFTS.type || it.beneficiaryType == SendMoneyBeneficiaryType.DOMESTIC.type }
            }
            else -> list
        }
    }

    private fun getY2YAndSMBeneficiaries(success: (ArrayList<IBeneficiary>) -> Unit) {
        fetchCombinedBeneficiariesApis { sendMoneyBeneficiariesResponse, y2yBeneficiariesResponse ->
            launch(Dispatcher.Main) {
                val combinedList: ArrayList<IBeneficiary> = arrayListOf()
                when (sendMoneyBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        combinedList.addAll(
                            sendMoneyBeneficiariesResponse.data.data as ArrayList<IBeneficiary>
                        )
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
                when (y2yBeneficiariesResponse) {
                    is RetroApiResponse.Success -> {
                        y2yBeneficiariesResponse.data.data?.forEach {
                            it.mobileNo =
                                Utils.getFormattedPhoneNumber(context, it.countryCode + it.mobileNo)
                        }
                        combinedList.addAll(y2yBeneficiariesResponse.data.data?.filter { it.yapUser == true } as ArrayList<IBeneficiary>)
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                    }
                }
                success(combinedList)
            }
        }
    }

    private fun fetchCombinedBeneficiariesApis(
        responses: (RetroApiResponse<GetAllBeneficiaryResponse>?, RetroApiResponse<Y2YBeneficiariesResponse>?) -> Unit
    ) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            coroutineScope {
                val deferredSM = async { repository.getAllBeneficiaries() }
                val deferredY2Y = async { repository.getY2YBeneficiaries(getLocalContacts()) }
                responses(deferredSM.await(), deferredY2Y.await())
            }
        }
    }

    private suspend fun getLocalContacts() = viewModelBGScope.async(Dispatchers.IO) {
        fetchContacts(context)
    }.await()

    private fun fetchContacts(context: Context): MutableList<Contact> {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val defaultCountryCode = Utils.getDefaultCountryCode(context)
            val phoneUtil = PhoneNumberUtil.getInstance()

            val contacts = LinkedHashMap<Long, Contact>()

            val projection = arrayOf(
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Data.DISPLAY_NAME_PRIMARY,
                //ContactsContract.Data.STARRED,
                //ContactsContract.Data.PHOTO_URI,
                ContactsContract.Data.PHOTO_THUMBNAIL_URI,
                ContactsContract.Data.DATA1,
                ContactsContract.Data.MIMETYPE
                //ContactsContract.Data.IN_VISIBLE_GROUP
            )
            val cursor = context.contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                generateSelection(), null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY + " ASC"
            )

            if (cursor != null) {
                cursor.moveToFirst()
                val idColumnIndex = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
                val displayNamePrimaryColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
                val thumbnailColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Data.PHOTO_THUMBNAIL_URI)
                val mimetypeColumnIndex = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
                val dataColumnIndex = cursor.getColumnIndex(ContactsContract.Data.DATA1)
                while (!cursor.isAfterLast) {
                    if (viewModelBGScope.isActive) {
                        val id = cursor.getLong(idColumnIndex)
                        var contact = contacts[id]
                        if (contact == null) {

                            contact = Contact()
                            val displayName = cursor.getString(displayNamePrimaryColumnIndex)
                            if (displayName != null && displayName.isNotEmpty()) {
                                contact.title = displayName
                            }
                            mapThumbnail(cursor, contact, thumbnailColumnIndex)
                            contacts[id] = contact
                        }
                        val mimetype = cursor.getString(mimetypeColumnIndex)
                        when (mimetype) {
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> mapEmail(
                                cursor,
                                contact,
                                dataColumnIndex
                            )
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                                var phoneNumber: String? = cursor.getString(dataColumnIndex)
                                if (phoneNumber != null && phoneNumber.isNotEmpty()) {
                                    phoneNumber = phoneNumber.replace("\\s+".toRegex(), "")

                                    try {
                                        val pn =
                                            phoneUtil.parse(phoneNumber, defaultCountryCode)
                                        contact.mobileNo = pn.nationalNumber.toString()
                                        contact.countryCode = "00${pn.countryCode}"
                                    } catch (e: Exception) {
                                    }
                                }
                            }
                        }
                        cursor.moveToNext()
                    } else break
                }
                cursor.close()
            }
            return (ArrayList(contacts.values) as MutableList<Contact>)
        }
        return mutableListOf()
    }

    private fun generateSelection(): String {
        val mSelectionBuilder = StringBuilder()
        if (mSelectionBuilder.isNotEmpty())
            mSelectionBuilder.append(" AND ")
        mSelectionBuilder.append(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
            .append(" = 1")
        return mSelectionBuilder.toString()
    }

    private fun mapThumbnail(cursor: Cursor, contact: Contact, columnIndex: Int) {
        val uri = cursor.getString(columnIndex)
        if (uri != null && uri.isNotEmpty()) {
            contact.beneficiaryPictureUrl = uri//Uri.parse(uri)
        }
    }

    private fun mapEmail(cursor: Cursor, contact: Contact, columnIndex: Int) {
        val email = cursor.getString(columnIndex)
        if (email != null && email.isNotEmpty()) {
            contact.email = email
        }
    }

    override fun requestDeleteBeneficiary(beneficiaryId: Int) {
        launch {
            state.loading = true
            when (val response = repository.deleteBeneficiaryFromList(beneficiaryId.toString())) {
                is RetroApiResponse.Success -> {
                    state.loading = false
                    state.toast = "Deleted Successfully"
                    requestRecentBeneficiaries(state.sendMoneyType.get() ?: "")
                    onDeleteSuccess.setValue(111)
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = "${response.error.message}^${AlertType.DIALOG.name}"
                }
            }
        }
    }

    override fun getY2YBeneficiaries() {

        pagingState.value = PagingState.LOADING
        if (listIsEmpty()) {
            launch {
                val localContacts = getLocalContacts()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    localContacts.removeIf { it.mobileNo == SessionManager.user?.currentCustomer?.mobileNo }
                } else {
                    localContacts.remove(localContacts.find { it.mobileNo == SessionManager.user?.currentCustomer?.mobileNo })
                }

                if (localContacts.isEmpty()) {
                    phoneContactLiveData.value = mutableListOf()
                    pagingState.value = PagingState.DONE
                } else {
                    val combineContacts = arrayListOf<Contact>()
                    val threshold = 3000
                    var lastCount = 0
                    val numberOfIteration =
                        ceil((localContacts.size.toDouble()) / threshold.toDouble()).toInt()
                    for (x in 1..numberOfIteration) {
                        val itemsToPost = localContacts.subList(
                            lastCount,
                            if ((x * threshold) > localContacts.size) localContacts.size else x * threshold
                        )
                        viewModelBGScope.async(Dispatchers.IO) {
                            when (val response =
                                repository.getY2YBeneficiaries(itemsToPost)) {
                                is RetroApiResponse.Success -> {
                                    response.data.data?.let { combineContacts.addAll(it) }
                                    if (combineContacts.size >= localContacts.size) {
                                        combineContacts.sortBy { it.title }
                                        phoneContactLiveData.postValue(combineContacts)
                                        pagingState.postValue(PagingState.DONE)
                                    }
                                }
                                is RetroApiResponse.Error -> {
                                    //state.toast = response.error.message
                                    pagingState.postValue(PagingState.ERROR)
                                    viewModelBGScope.close()
                                }
                            }
                        }
                        lastCount = x * threshold
                    }
                }
            }
        } else {
            phoneContactLiveData.postValue(phoneContactLiveData.value)
            pagingState.postValue(PagingState.DONE)
        }
    }
}