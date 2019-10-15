package co.yap.modules.dashboard.sendmoney.home.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.dashboard.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.dashboard.sendmoney.home.viewmodels.SendMoneyNoContactsViewModel


class SendMoneyNoContactsFragment : SendMoneyBaseFragment<ISendMoneyHome.ViewModel>(),
    ISendMoneyHome.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_send_money_no_contacts

    override val viewModel: ISendMoneyHome.ViewModel
        get() = ViewModelProviders.of(this).get(SendMoneyNoContactsViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onPause() {
        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onResume() {
        super.onResume()

//        viewModel.clickEvent.observe(this, Observer {
//            when (it) {
//
//            }
//        })

    }

    override fun onBackPressed(): Boolean {

        return super.onBackPressed()
    }


}