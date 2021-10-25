package co.yap.billpayments.billcategory

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BaseState

class BillCategoryState : BaseState(), IBillCategory.State {
    override var dataPopulated: ObservableBoolean = ObservableBoolean(false)
}
