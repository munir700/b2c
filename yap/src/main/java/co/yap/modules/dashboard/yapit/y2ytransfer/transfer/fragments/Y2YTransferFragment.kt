package co.yap.modules.dashboard.yapit.y2ytransfer.transfer.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.modules.dashboard.yapit.y2ytransfer.fragments.Y2YBaseFragment
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.interfaces.IY2YTransfer
import co.yap.modules.dashboard.yapit.y2ytransfer.transfer.viewmodels.Y2YTransferViewModel
import co.yap.yapcore.BR

class Y2YTransferFragment : Y2YBaseFragment<IY2YTransfer.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_yap_to_yap_transfer

    override val viewModel: Y2YTransferViewModel
        get() = ViewModelProviders.of(this).get(Y2YTransferViewModel::class.java)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // viewModel.clickEvent.observe(this, clickEventObserver)
    }

    private val clickEventObserver = Observer<Int> {
        when (it) {
            R.id.btnInvite -> {
                showToast("Invitie Button Clicked")
            }
        }
    }
}