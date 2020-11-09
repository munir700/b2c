package co.yap.modules.dashboard.yapit.sendmoney.homecountry

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.yapit.sendmoney.main.SendMoneyBaseFragment
import co.yap.yapcore.BaseBindingFragment

class SMHomeCountryFragment: SendMoneyBaseFragment<ISMHomeCountry.ViewModel>(), ISMHomeCountry.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_send_money_home

    override val viewModel: ISMHomeCountry.ViewModel
    get() = ViewModelProviders.of(this).get(SMHomeCountryViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}