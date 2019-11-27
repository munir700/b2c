package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.y2y.main.viewmodels.Y2YBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.PagingState
import co.yap.yapcore.helpers.Utils
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive

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

    private fun fetchContacts(context: Context): MutableList<Contact> {

        val contacts: MutableList<Contact> = ArrayList()
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            var defaultCountryCode = Utils.getDefaultCountryCode(context)
            val phoneUtil = PhoneNumberUtil.getInstance()

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

                                var phoneNo =
                                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                                // contactId for email
                                //val contactId =
                                //    it.getLong(it.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID))
                                // contactId2 for email
                                val contactId =
                                    it.getLong(it.getColumnIndex(ContactsContract.Data.CONTACT_ID))

                                try {
                                    val pn =
                                        phoneUtil.parse(phoneNo, defaultCountryCode)
                                    pn.nationalNumber.toString()
                                    phoneNo = pn.nationalNumber.toString()
                                    defaultCountryCode = "00${pn.countryCode}"
                                } catch (e: Exception) {
                                }

                                val contact = Contact(
                                    name,
                                    defaultCountryCode,
                                    phoneNo,
                                    "",
                                    contactId.toString(),
                                    false, null
                                )
                                contacts.add(contact)
                            }
                            if (contacts.size == 200)
                                break
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

//    interface UploadIdCardRetroService {
//
//        @POST(CustomersRepository.URL_Y2Y_BENEFICIARIES)
//        fun getY2YBeneficiaries(@Body contacts: List<Contact>): Call<Y2YBeneficiariesResponse>
//    }
//
//    fun uploadDocument(list: MutableList<Contact>) {
//
//        val logger = HttpLoggingInterceptor()
//        logger.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder()
//            .connectTimeout(100, TimeUnit.SECONDS)
//            .writeTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(100, TimeUnit.SECONDS)
//            .addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("X-XSRF-TOKEN", "f0a64479-cdd7-4fe8-a7a4-fcc10dc3180p")
//                    .addHeader("Cookie:XSRF-TOKEN", "f0a64479-cdd7-4fe8-a7a4-fcc10dc3180p")
//                    .addHeader(
//                        "Authorization",
//                        "Bearer eyJraWQiOiI3OTE3YzAyNC0xN2M1LTRjOWYtYWI1OC05ZmFjZWI2Njg2NzYiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJub3RlMTBwbHVzMDAxQHlhcC5jb3xmMGFiZGY1Ni1jMjk4LTQ1NTctOTIzZC05OTQzZDM0YmJlNGMiLCJhdWQiOiJlM2M3ZGJhZC1mMTdmLTQ4ZjYtOTE5My01NzY4Y2RiNDY2ZTgiLCJpc3MiOiJodHRwczovL3NlbGYtaXNzdWVkLm1lIiwiZXhwIjoxNTc0Nzc1MzE3LCJpYXQiOjE1NzQ3NzE3MTcsImp0aSI6ImVhMDk0ZTliLTY1OWItNGYxYy05OWQxLWZmOTQ3MmJkN2YzNyJ9.RGtgmPbeElJee0Fqj4uvF2-WBKBVfJElexOxUtm_uF97k-BaKpltfOZpj6Xv74NrPKFUgQn4KWqamN13C87ehaQObYMNJG4P_wIIaWsoiZss5SZS39DCYj9_8IgEALjedv1aCj1Q4sVhNT3EucpGY8jYoLdwHoddiYSwgAScP3mbMMMqKQqWKhVMc8Vqm1wq3PWIPAsyu0Imlgu0Kr9p-CFI2kybl2conoLbb7TubpYIInJE_CZPjZf11w0qb2xU95PhYueK--oA9uowoUXqNH53Hs_06ylzL7VgAhA3F-w3hBflm95qRGg2VxUahthcFbQNxG-_fx7lSgOY2uNt5w"
//                    )
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//        val retro: Retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.0.96:8080/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client).build()
//        val service = retro.create(UploadIdCardRetroService::class.java)
////        val file = File(result.document.files[1].croppedFile)
////        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
////        val part =
////            MultipartBody.Part.createFormData("image", file.name, fileReqBody)
////        state.loading = true
//        service.getY2YBeneficiaries(list).enqueue(object : Callback<Y2YBeneficiariesResponse> {
//            override fun onFailure(call: Call<Y2YBeneficiariesResponse>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//            override fun onResponse(
//                call: Call<Y2YBeneficiariesResponse>,
//                response: Response<Y2YBeneficiariesResponse>?
//            ) {
//                response?.body()?.data
//            }
//        })
//    }
}