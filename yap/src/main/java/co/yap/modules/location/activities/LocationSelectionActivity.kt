package co.yap.modules.location.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.location.interfaces.ILocationSelection
import co.yap.modules.location.viewmodels.LocationSelectionViewModel
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingActivity

class LocationSelectionActivity : BaseBindingActivity<ILocationSelection.ViewModel>(),
    ILocationSelection.View {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.activity_address_selection
    override val viewModel: ILocationSelection.ViewModel
        get() = ViewModelProviders.of(this).get(LocationSelectionViewModel::class.java)

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LocationSelectionActivity::class.java)
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                viewModel.address =
                    Address(viewModel.state.addressTitle, viewModel.state.addressSubtitle, 0.0, 0.0)
            }
        }
    }
}