package co.yap.billpayments.paybills

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.paybills.adapter.DueBill
import co.yap.billpayments.paybills.adapter.DueBillsAdapter
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

class PayBillsViewModel(application: Application) :
    PayBillBaseViewModel<IPayBills.State>(application),
    IPayBills.ViewModel {

    override val state: IPayBills.State = PayBillsState()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val dueBillsAdapter: DueBillsAdapter = DueBillsAdapter(arrayListOf())

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_bill_payment_text_title))
        toggleToolBarVisibility(true)
        parentViewModel?.state?.leftIconVisibility?.set(true)
    }

    override fun onCreate() {
        super.onCreate()
        dueBillsAdapter.setList(getDueBills())
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    private fun getDueBills(): ArrayList<DueBill> {
        val arr = ArrayList<DueBill>()
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2021-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2020-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2020-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )
        arr.add(
            DueBill(
                logoUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                billerName = "Etisalat",
                billNickName = "My iPhoneX",
                billDueDate = "2021-08-12T06:53:35",
                amount = "250.000",
                currency = "AED"
            )
        )

        return arr
    }
}