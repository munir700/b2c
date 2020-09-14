package co.yap.modules.dashboard.store.young.contact

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungContactDetailsVM @Inject constructor(
    override val state: IYoungContactDetails.State,
    override var validator: Validator?
) : DaggerBaseViewModel<IYoungContactDetails.State>(), IYoungContactDetails.ViewModel, IValidator {
    override val clickEvent = SingleClickEvent()
    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}
