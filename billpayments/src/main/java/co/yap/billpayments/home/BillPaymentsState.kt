package co.yap.billpayments.home

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillPaymentsState : BaseState(), IBillPayments.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var toolbarTitleString: ObservableField<String> = ObservableField("")
    override var rightFirstIconDrawable: ObservableField<Drawable> = ObservableField()
    override var rightSecondIconDrawable: ObservableField<Drawable> = ObservableField()
    override var rightFirstIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var rightSecondIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(true)
}
