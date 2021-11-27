package co.yap.billpayments.dashboard

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.yapcore.BaseState

class BillPaymentsState : BaseState(), IBillPayments.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var toolbarTitleString: ObservableField<String> = ObservableField("")
    override var rightIconDrawable: ObservableField<Drawable> = ObservableField()
    override var sortIconVisibility: ObservableBoolean = ObservableBoolean()
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean()
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(true)
    override var totalDueAmount: ObservableField<String> = ObservableField("")
}
