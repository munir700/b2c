package co.yap.billpayments.dashboard.editbill

import android.app.Application
import android.view.View
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailAdapter
import co.yap.billpayments.addbiller.addbillerdetail.composer.AddBillerDetailInputComposer
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.interfaces.OnItemClickListener

class EditBillViewModel(application: Application) :
    BillDashboardBaseViewModel<IEditBill.State>(application),
    IEditBill.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val state: IEditBill.State = EditBillState()
    override val addBillerDetailItemComposer: AddBillerDetailInputComposer =
        AddBillerDetailInputComposer()
    override var adapter: AddBillerDetailAdapter = AddBillerDetailAdapter(mutableListOf())
    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        setEditBillDetails()
        adapter.setItemListener(listener)
    }

    override fun onResume() {
        super.onResume()
        toggleToolBarVisibility(true)
        setToolBarTitle(parentViewModel?.selectedBill?.billerInfo?.billerName.toString())
        toggleRightIconVisibility(false)
        state.screenTitle.set(getString(Strings.screen_edit_bill_title_text))
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    val listener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (lengthValidation())
                state.valid.set(textChangedValidation())
            else
                state.valid.set(false)
        }
    }

    private fun textChangedValidation(): Boolean {
        adapter.getDataList().forEachIndexed { index, field ->
            if (field.value?.get() != parentViewModel?.selectedBill?.inputsData?.get(index)?.value) {
                return true
            }
        }
        return false
    }

    private fun lengthValidation(): Boolean {
        var isValid = false
        for (field in adapter.getDataList()) {
            if (field.minLength != null &&
                field.minLength!! > field.value?.get()?.length ?: 0
            ) {
                isValid = false
                break
            } else {
                isValid = true
            }
        }
        return isValid
    }

    override fun setEditBillDetails() {
        var list =
            addBillerDetailItemComposer.compose(parentViewModel?.selectedBill?.billerInfo?.skuInfos?.ioCatalogs as ArrayList<IoCatalogModel>)
        list.removeAt(0)
        list.forEachIndexed { index, item ->
            item.value?.set(parentViewModel?.selectedBill?.inputsData?.get(index)?.value)
        }
        adapter.setList(list)
    }
}
