package co.yap.widgets.scanqrcode

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.Contact
import co.yap.networking.customers.requestdtos.QRContactRequest
import co.yap.networking.customers.responsedtos.QRContact
import co.yap.networking.customers.responsedtos.QRContactResponse
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel

class ScanQRCodeViewModel(application: Application) :
    BaseViewModel<IScanQRCode.State>(application),
    IScanQRCode.ViewModel {
    override val state: IScanQRCode.State = ScanQRCodeState()
    private val customerRepository: CustomersRepository = CustomersRepository
    override var contactInfo: MutableLiveData<Contact> = MutableLiveData()

    override fun uploadQRCode(uuid: String?) {
        launch {
            state.loading=true
            when (val response = customerRepository.getQRContact(
                QRContactRequest(uuid = uuid?:""))) {
                is RetroApiResponse.Success -> {
                    state.loading=false
                    response.data.qrContact.let {
                        contactInfo.value = Contact(
                            beneficiaryPictureUrl = it?.profilePictureName,
                            countryCode = it?.countryCode,
                            mobileNo = it?.mobileNo
                        )
                    }
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading=false
                }
            }
        }
    }
}