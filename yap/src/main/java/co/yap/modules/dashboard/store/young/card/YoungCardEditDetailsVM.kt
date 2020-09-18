package co.yap.modules.dashboard.store.young.card

import android.os.Bundle
import androidx.databinding.ObservableField
import androidx.navigation.NavController
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.dagger.base.viewmodel.DaggerBaseViewModel
import javax.inject.Inject

class YoungCardEditDetailsVM @Inject constructor(
    override val state: IYoungCardEditDetails.State
) : DaggerBaseViewModel<IYoungCardEditDetails.State>(), IYoungCardEditDetails.ViewModel {
    override val adapter: ObservableField<YoungCardEditAdapter>? = ObservableField()
    override fun onFirsTimeUiCreate(bundle: Bundle?, navigation: NavController?) {
    }

    override fun handleOnClick(id: Int) {


    }


}
