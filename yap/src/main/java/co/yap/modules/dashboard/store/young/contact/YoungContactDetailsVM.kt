package co.yap.modules.dashboard.store.young.contact

import android.os.Bundle
import androidx.navigation.NavController
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import co.yap.yapcore.hilt.base.viewmodel.HiltBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YoungContactDetailsVM @Inject constructor(
    override val state: YoungContactDetailsState
) : HiltBaseViewModel<IYoungContactDetails.State>(), IYoungContactDetails.ViewModel, IValidator {
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
    override var validator: Validator? = Validator( null)
    override fun handleOnClick(id: Int) {
    }
}
