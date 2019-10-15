package co.yap.modules.dashboard.sendmoney.addbeneficiary.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.sendmoney.addbeneficiary.interfaces.ITransferType
import co.yap.modules.dashboard.sendmoney.addbeneficiary.viewmodels.TransferTypeViewModel
import co.yap.modules.dashboard.sendmoney.fragments.SendMoneyBaseFragment

class TransferTypeFragment : SendMoneyBaseFragment<ITransferType.ViewModel>(),
    ITransferType.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_transfer_type

    override val viewModel: ITransferType.ViewModel
        get() = ViewModelProviders.of(this).get(TransferTypeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.llBankTransferType -> {

                }

                R.id.llCashPickUpTransferType -> {

                }
            }
        })


    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


}