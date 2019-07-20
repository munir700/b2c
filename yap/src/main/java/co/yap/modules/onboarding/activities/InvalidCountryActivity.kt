package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IInvalidCountry
import co.yap.modules.onboarding.viewmodels.InvalidCountryViewModel
import co.yap.yapcore.BaseBindingActivity

class InvalidCountryActivity : BaseBindingActivity<IInvalidCountry.ViewModel>() {
    companion object {
        fun newIntent(context: Context): Intent = Intent(context, InvalidCountryActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_invalid_country
    override val viewModel: IInvalidCountry.ViewModel
        get() = ViewModelProviders.of(this).get(InvalidCountryViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.gotoDashboardPressEvent.observe(this, gotoDashboardpressEventObserver)
    }

    private val gotoDashboardpressEventObserver = Observer<Boolean> {
        //startActivity(LiteDashboardActivity.newIntent(this@InvalidCountryActivity)
    }
}