package co.yap.billpayments.mybills

import android.app.Application
import co.yap.billpayments.base.PayBillBaseViewModel
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.billpayments.mybills.adapter.MyBillsModel
import co.yap.yapcore.enums.BillStatus

class MyBillsViewModel(application: Application) :
    PayBillBaseViewModel<IMyBills.State>(application), IMyBills.ViewModel {
    override val state: IMyBills.State = MyBillsState()
    override var adapter: MyBillsAdapter = MyBillsAdapter(mutableListOf())
    override var myBills: MutableList<MyBillsModel> = mutableListOf()

    override fun onCreate() {
        super.onCreate()
        myBills = getMyBillList()
        adapter.setList(myBills)
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("You have ${myBills.size} bills registered")
    }

    private fun getMyBillList(): MutableList<MyBillsModel> {
        return mutableListOf<MyBillsModel>(
            MyBillsModel(
                iconUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            MyBillsModel(
                iconUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            MyBillsModel(
                iconUrl = "",
                name = "Salik",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.BILL_DUE.title
            ),
            MyBillsModel(
                iconUrl = "https://s3-eu-west-1.amazonaws.com/dev-b-yap-documents-public/profile_image/customer_data/3000000207/documents/1588940062805_profile_photo.jpg",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "AED",
                amount = "100",
                billStatus = BillStatus.OVERDUE.title
            ),
            MyBillsModel(
                iconUrl = "",
                name = "Etsilat",
                description = "29 Sept 2020 - Burj Telecom Residences",
                currency = "USD",
                amount = "100",
                billStatus = BillStatus.PAID.title
            )
        )
    }

}
