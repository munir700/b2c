package co.yap.modules.dashboard.store.young.card

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import co.yap.yapcore.helpers.validation.IValidator
import co.yap.yapcore.helpers.validation.Validator
import javax.inject.Inject

class YoungCardEditDetailsVM @Inject constructor(
    override val state: IYoungCardEditDetails.State
) : DaggerBaseViewModel<IYoungCardEditDetails.State>(), IYoungCardEditDetails.ViewModel {
    override val adapter: ObservableField<YoungCardEditAdapter>? = ObservableField()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnClick(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }
}
