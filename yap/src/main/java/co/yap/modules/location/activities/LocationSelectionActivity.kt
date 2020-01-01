package co.yap.modules.location.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.modules.location.viewmodels.LocationSelectionViewModel
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity

class LocationSelectionActivity : BaseBindingActivity<ILocationSelection.ViewModel>(),
    ILocationSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_address_selection
    override val viewModel: ILocationSelection.ViewModel
        get() = ViewModelProviders.of(this).get(LocationSelectionViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}