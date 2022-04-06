package co.yap.modules.dashboard.yapit.topup.addcard

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator

class AddTopUpCardViewModel(application: Application) :
    BaseViewModel<IAddTopUpCard.State>(application), IAddTopUpCard.ViewModel, IValidator,
    Validator.ValidationListener {
    override val state: AddTopUpCardState =
        AddTopUpCardState()
    override var validator: Validator?= Validator(null)


}