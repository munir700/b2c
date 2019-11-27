package co.yap.modules.dashboard.yapit.y2y.home.phonecontacts

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


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

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            //            var defaultCountryCode = Utils.getDefaultCountryCode(context)
//            val phoneUtil = PhoneNumberUtil.getInstance()

            val contacts = HashMap<Long, Contact>()

            val projection = arrayOf(
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Data.DISPLAY_NAME_PRIMARY,
                ContactsContract.Data.STARRED,
                ContactsContract.Data.PHOTO_URI,
                ContactsContract.Data.PHOTO_THUMBNAIL_URI,
                ContactsContract.Data.DATA1,
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.IN_VISIBLE_GROUP
            )
            val cursor = context.contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                projection,
                null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )

            if (cursor != null) {
                cursor.moveToFirst()

                val idColumnIndex = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
                //val inVisibleGroupColumnIndex =
                //    cursor.getColumnIndex(ContactsContract.Data.IN_VISIBLE_GROUP)
                val displayNamePrimaryColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
                //val starredColumnIndex = cursor.getColumnIndex(ContactsContract.Data.STARRED)
                //val photoColumnIndex = cursor.getColumnIndex(ContactsContract.Data.PHOTO_URI)
                val thumbnailColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Data.PHOTO_THUMBNAIL_URI)
                val mimetypeColumnIndex = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
                val dataColumnIndex = cursor.getColumnIndex(ContactsContract.Data.DATA1)

                while (!cursor.isAfterLast) {
                    val id = cursor.getLong(idColumnIndex)
                    var contact = contacts[id]
                    if (contact == null) {

                        contact = Contact()
                        val displayName = cursor.getString(displayNamePrimaryColumnIndex)
                        if (displayName != null && displayName.isNotEmpty()) {
                            contact.title = displayName
                        }

                        contact.countryCode = "0092"

                        val mimetype = cursor.getString(mimetypeColumnIndex)
                        when (mimetype) {
                            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                                val email = cursor.getString(dataColumnIndex)
                                if (email != null && email.isNotEmpty()) {
                                    contact.email = email
                                }
                            }
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                                var phoneNumber = cursor.getString(dataColumnIndex)
                                if (phoneNumber != null && phoneNumber.isNotEmpty()) {
                                    // Remove all whitespaces
                                    phoneNumber = phoneNumber.replace("\\s+".toRegex(), "")
                                    contact.mobileNo = phoneNumber
                                }
                            }
                        }
                        val uri = cursor.getString(thumbnailColumnIndex)
                        if (uri != null && !uri.isEmpty()) {
                            contact.beneficiaryPictureUrl = Uri.parse(uri).toString()
                        }

                        contacts[id] = contact
                    }
                    cursor.moveToNext()
                }
                cursor.close()
            }
            return (ArrayList(contacts.values.take(50)) as MutableList<Contact>)
        }
        return mutableListOf()
    }

//    try {
//                                    val pn =
//                                        phoneUtil.parse(phoneNo, defaultCountryCode)
//                                    pn.nationalNumber.toString()
//                                    phoneNo = pn.nationalNumber.toString()
//                                    defaultCountryCode = "00${pn.countryCode}"
//                                } catch (e: Exception) {
//                                }
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