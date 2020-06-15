package co.yap.modules.dashboard.store.household.userinfo

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class HHAddUserNameVM @Inject constructor(override val state: IHHAddUserName.State,
                                          override var validator: Validator?) :
    DaggerBaseViewModel<IHHAddUserName.State>(), IHHAddUserName.ViewModel, IValidator {
    override val clickEvent = SingleClickEvent()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override fun handlePressOnClick(id: Int) {
        clickEvent.postValue(id)
        validator?.validate()
    }
}
