package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.responsedtos.Y2YBeneficiariesResponse
import co.yap.networking.intercepters.CookiesInterceptor
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.ceil


class PhoneContactViewModel(application: Application) :
    Y2YBaseViewModel<IPhoneContact.State>(application),
    IPhoneContact.ViewModel, IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val state: IPhoneContact.State = PhoneContactState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private var pagingState: MutableLiveData<PagingState> = MutableLiveData()
    override var phoneContactLiveData: MutableLiveData<List<Contact>> = MutableLiveData()

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
            Timber.d(
                ("contact time fetching local start" + android.text.format.DateFormat.format(
                    "yyyy-MM-dd hh:mm:ss a",
                    java.util.Date()
                ))
            )
            val localContacts = getLocalContacts()
            Timber.d(
                ("contact time fetching local end" + android.text.format.DateFormat.format(
                    "yyyy-MM-dd hh:mm:ss a",
                    java.util.Date()
                ))
            )
            if (localContacts.isEmpty()) {
                phoneContactLiveData.value = mutableListOf()
                pagingState.value = PagingState.DONE
            } else {

                val threshold = 300
                var lastCount = 0
                kotlinx.coroutines.delay(1000)

                //uploadDocument(localContacts)

                val number = ceil((localContacts.size.toDouble()) / threshold.toDouble())
                for (x in 1..number.toInt()) {
                    viewModelBGScope.async(Dispatchers.IO) {
                        when (val response =
                            repository.getY2YBeneficiaries(
                                localContacts.subList(
                                    lastCount,
                                    if ((x * threshold) > localContacts.size) localContacts.size else x * threshold
                                )
                            )) {
                            is RetroApiResponse.Success -> {
                                phoneContactLiveData.postValue(response.data.data)
                                pagingState.postValue(PagingState.DONE)

                            }
                            is RetroApiResponse.Error -> {
                                //state.toast = response.error.message
                                pagingState.postValue(PagingState.ERROR)
                            }
                        }
                    }.await()
                    lastCount = x * threshold
                }
            }
        }
    }

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
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
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

    interface UploadIdCardRetroService {

        @POST(CustomersRepository.URL_Y2Y_BENEFICIARIES)
        fun getY2YBeneficiaries(@Body contacts: List<Contact>): Call<Y2YBeneficiariesResponse>
    }

    fun uploadDocument(list: MutableList<Contact>) {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(CookiesInterceptor())
            .addInterceptor(logger)
            .build()
        val retro: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.96:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build()
        val service = retro.create(UploadIdCardRetroService::class.java)
//        val file = File(result.document.files[1].croppedFile)
//        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
//        val part =
//            MultipartBody.Part.createFormData("image", file.name, fileReqBody)
//        state.loading = true
        list.apply {
            list.addAll(list)
            list.addAll(list)
            list.addAll(list)

        }

        Timber.d(
            ("contact time live request start ${list.size}" + android.text.format.DateFormat.format(
                "yyyy-MM-dd hh:mm:ss a",
                java.util.Date()
            ))
        )
        service.getY2YBeneficiaries(list).enqueue(object : Callback<Y2YBeneficiariesResponse> {
            override fun onFailure(call: Call<Y2YBeneficiariesResponse>, t: Throwable) {
                t.printStackTrace()
                Timber.d(
                    ("contact time live request End" + android.text.format.DateFormat.format(
                        "yyyy-MM-dd hh:mm:ss a",
                        java.util.Date()
                    ))
                )
                state.toast = t.message.toString()
                pagingState.postValue(PagingState.ERROR)
            }

            override fun onResponse(
                call: Call<Y2YBeneficiariesResponse>,
                response: Response<Y2YBeneficiariesResponse>?
            ) {

                Timber.d(
                    ("contact time live request End" + android.text.format.DateFormat.format(
                        "yyyy-MM-dd hh:mm:ss a",
                        java.util.Date()
                    ))
                )
                phoneContactLiveData.postValue(response?.body()?.data)
                pagingState.postValue(PagingState.DONE)

            }
        })
    }
}