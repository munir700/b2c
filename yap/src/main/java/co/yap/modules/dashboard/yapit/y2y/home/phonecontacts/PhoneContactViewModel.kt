package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.modules.kyc.enums.DocScanStatus
import co.yap.modules.kyc.fragments.CardScanResponse
import co.yap.modules.kyc.fragments.UploadIdCardRetroService
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.Y2YBeneficiariesResponse
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import com.digitify.identityscanner.core.arch.Gender
import com.digitify.identityscanner.docscanner.models.Identity
import com.digitify.identityscanner.docscanner.models.IdentityScannerResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

class PhoneContactViewModel(application: Application) :
    Y2YBaseViewModel<IPhoneContact.State>(application),
    IPhoneContact.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPhoneContact.State = PhoneContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private var pagingState: MutableLiveData<PagingState> = MutableLiveData()
    override var phoneContactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()

//    // TODO Remove this method
//    fun uploadDocument(result: IdentityScannerResult) {
//        val logger = HttpLoggingInterceptor()
//        logger.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder()
//            .connectTimeout(100, TimeUnit.SECONDS)
//            .writeTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(100, TimeUnit.SECONDS)
//            .addInterceptor(logger).build()
//        val retro: Retrofit = Retrofit.Builder()
//            .baseUrl("http://172.21.200.181:8000/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client).build()
//        val service = retro.create(UploadIdCardRetroService::class.java)
//        val file = File(result.document.files[1].croppedFile)
//        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
//        val part =
//            MultipartBody.Part.createFormData("image", file.name, fileReqBody)
//        state.loading = true
//        service.uploadIdCard(file = part).enqueue(object : Callback<CardScanResponse> {
//            override fun onFailure(call: Call<CardScanResponse>, t: Throwable) {
//                state.loading = false
//            }
//
//            override fun onResponse(
//                call: Call<CardScanResponse>,
//                response: Response<CardScanResponse>?
//            ) {
//                state.loading = false
//                if (response?.body()?.success!!) {
//                    var identity = Identity()
//                    identity.nationality = response.body()?.nationality
//                    identity.gender =
//                        if (response.body()?.sex.equals("M")) Gender.Male else Gender.Female
//                    identity.sirName = response.body()?.surname
//                    identity.givenName = response.body()?.names
//                    identity.citizenNumber = response.body()?.number
//                    identity.expirationDate =
//                        DateUtils.stringToDate(response.body()?.expiration_date!!, "yyMMdd")
//                    identity.dateOfBirth =
//                        DateUtils.stringToDate(response.body()?.date_of_birth!!, "yyMMdd")
//
//                    result.identity = identity
//                    parentViewModel?.identity = result
//                    state.eidScanStatus = DocScanStatus.SCAN_COMPLETED
//                } else {
//                    state.toast = getString(
//                        Strings.idenetity_scanner_sdk_screen_review_info_display_text_error_not_readable
//                    )
//                }
//
//            }
//        })
//
//    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun getState(): LiveData<PagingState> {
        return pagingState
    }

    override fun listIsEmpty(): Boolean {
        return phoneContactLiveData.value?.isEmpty() ?: true
    }

    private suspend fun getLocalContacts() = viewModelBGScope.async(Dispatchers.IO) {
        fetchContacts(context)
    }.await()

    override fun onCleared() {
        viewModelBGScope.close()
        super.onCleared()
    }

    override fun getY2YBeneficiaries() {

        pagingState.value = PagingState.LOADING
        launch {
            loadData()
            val localContacts = getLocalContacts()
            if (localContacts.isEmpty()) {
                phoneContactLiveData.value = mutableListOf()
                pagingState.value = PagingState.DONE
            } else {
                when (val response = repository.getY2YBeneficiaries(localContacts)) {
                    is RetroApiResponse.Success -> {
                        phoneContactLiveData.value = response.data.data
                        pagingState.value = PagingState.DONE
                    }
                    is RetroApiResponse.Error -> {
                        state.toast = response.error.message
                        pagingState.value = PagingState.ERROR
                    }
                }
            }
        }
    }

    /*
      * Defines an array that contains column names to move from
      * the Cursor to the ListView.
      */
    @SuppressLint("InlinedApi", "ObsoleteSdkInt")
    private val FROM_COLUMNS: Array<String> = arrayOf(
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        } else {
            ContactsContract.Contacts.DISPLAY_NAME
        }
    )

    private val PROJECTION: Array<out String> = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.LOOKUP_KEY,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )

    // Defines the text expression
    @SuppressLint("InlinedApi")
    private val SELECTION: String =
        "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} LIKE ?"


    private fun fetchContacts(context: Context): MutableList<Contact> {

        val contacts: MutableList<Contact> = ArrayList()
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val defaultCountryCode = Utils.getDefaultCountryCode(context)

//            val cursor = context.contentResolver.query(
//                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, SELECTION, null,
//                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
//            )

            val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )
            try {
                cursor?.let {
                    if (it.count > 0) {
                        while (it.moveToNext()) {
                            if (viewModelBGScope.isActive) {
                                val name =
                                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))

                                val phoneWihtoutCountryCode =
                                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                val phoneNo = Utils.getPhoneWithoutCountryCode(
                                    defaultCountryCode,
                                    phoneWihtoutCountryCode
                                )

                                val countryCode =
                                    Utils.getPhoneNumberCountryCodeForAPI(
                                        defaultCountryCode,
                                        phoneWihtoutCountryCode
                                    )

                                val contactId =
                                    it.getLong(it.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))

                                val contactId2 =
                                    it.getLong(it.getColumnIndex(ContactsContract.Data.CONTACT_ID))

                                val email = fetchContactsEmail(contactId)

                                var photoContentUri: Uri? = getPhotoUri(contactId2)
                                if (photoContentUri == null) photoContentUri = Uri.EMPTY

                                Timber.d("contacts: $name $phoneNo $email $countryCode $photoContentUri")

                                val contact = Contact(
                                    name,
                                    countryCode,
                                    phoneNo,
                                    email,
                                    photoContentUri?.toString(),
                                    false, null
                                )
                                contacts.add(contact)
                            }
                        }
                    }
                }
                cursor?.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return contacts
    }

    private fun getPhotoUri(contactId: Long): Uri? {
        val cursor = context.contentResolver.query(
            ContactsContract.Data.CONTENT_URI, null,
            ContactsContract.Data.CONTACT_ID
                    + "="
                    + contactId
                    + " AND "

                    + ContactsContract.Data.MIMETYPE
                    + "='"
                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                    + "'", null, null
        )
        try {
            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    cursor.close()
                    return null // no photo
                }
            } else {
                return null // error in cursor process
            }

        } catch (e: Exception) {
            cursor?.close()
            e.printStackTrace()
            return null
        }
        val person = ContentUris.withAppendedId(
            ContactsContract.Contacts.CONTENT_URI, contactId
        )
        return Uri.withAppendedPath(
            person,
            ContactsContract.Contacts.Photo.CONTENT_DIRECTORY
        )
    }

    private fun fetchContactsEmail(id: Long): String {
        var emlAdd = ""
        val PROJECTION = arrayOf(
            ContactsContract.CommonDataKinds.Email.CONTACT_ID,
            ContactsContract.CommonDataKinds.Email.DATA
        )
        val order = ("CASE WHEN "
                + ContactsContract.Contacts.DISPLAY_NAME
                + " NOT LIKE '%@%' THEN 1 ELSE 2 END, "
                + ContactsContract.Contacts.DISPLAY_NAME
                + ", "
                + ContactsContract.CommonDataKinds.Email.DATA
                + " COLLATE NOCASE")

        val filter =
            ContactsContract.CommonDataKinds.Email.DATA + " NOT LIKE '' AND " + ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + (id)
        val cur = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            PROJECTION,
            filter,
            null,
            order
        )
        if (cur!!.moveToFirst()) {
            do {
                emlAdd = cur.getString(1)
            } while (cur.moveToNext())
        }

        cur.close()
        return emlAdd
    }
}