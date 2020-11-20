package co.yap.sendmoney.y2y.home.yapcontacts

import androidx.databinding.Bindable
import co.yap.sendmoney.BR
import co.yap.yapcore.BaseState

class YapContactState : BaseState(), IYapContact.State {
    @get:Bindable
    override var listCountDescription: String=""
        set(value) {
            field=value
            notifyPropertyChanged(BR.listCountDescription)
        }
}