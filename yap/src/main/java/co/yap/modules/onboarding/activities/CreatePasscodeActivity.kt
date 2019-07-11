package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.viewmodels.CreatePasscodeViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.helpers.SharedPreferenceManager


class CreatePasscodeActivity : BaseBindingActivity<ICreatePasscode.ViewModel>() {
    override fun getBindingVariable(): Int = BR.createPasscodeViewModel

    override fun getLayoutId(): Int = R.layout.screen_create_passcode

    override val viewModel: ICreatePasscode.ViewModel
        get() = ViewModelProviders.of(this).get(CreatePasscodeViewModel::class.java)

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, CreatePasscodeActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)

    }

    private val nextButtonObserver = Observer<Boolean> {
        val intent = Intent()
        intent.putExtra(SharedPreferenceManager.KEY_PASSCODE, viewModel.state.passcode)
        setResult(Constants.REQUEST_CODE_CREATE_PASSCODE, intent)
        finish()
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
    }
}
