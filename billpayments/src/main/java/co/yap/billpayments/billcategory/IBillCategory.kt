package co.yap.billpayments.billcategory

import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.billpayments.billcategory.adapter.BillCategoryModel
import co.yap.yapcore.IBase

interface IBillCategory {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val adapter: BillCategoryAdapter
        var billcategories: MutableLiveData<MutableList<BillCategoryModel>>
    }

    interface State : IBase.State {
    }
}
