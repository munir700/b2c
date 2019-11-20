package co.yap.modules.yapit.sendmoney.home.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.yapit.sendmoney.fragments.SendMoneyBaseFragment
import co.yap.modules.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.modules.yapit.sendmoney.home.viewmodels.SendMoneyNoContactsViewModel


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

         viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.addContactsButton ->
                    findNavController().navigate(R.id.action_selectCountryFragment_to_transferTypeFragment)
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