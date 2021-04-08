package co.yap.billpayments.home

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillPaymentsState : BaseState(), IBillPayments.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var toolbarTitleString: ObservableField<String> = ObservableField("")
    override var rightIconDrawable: ObservableField<Drawable> = ObservableField()
    override var sortIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(true)
}
