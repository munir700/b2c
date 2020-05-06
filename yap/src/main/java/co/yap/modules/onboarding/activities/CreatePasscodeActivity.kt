package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.forgotpasscode.interfaces.ICreatePasscode
import co.yap.modules.forgotpasscode.viewmodels.CreatePasscodeViewModel
import co.yap.modules.onboarding.constants.Constants
import co.yap.modules.webview.WebViewFragment
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.constants.Constants.URL_TERMS_CONDITION
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.preventTakeScreenShot
import co.yap.yapcore.helpers.extentions.startFragment
import kotlinx.android.synthetic.main.activity_create_passcode.*


class CreatePasscodeActivity : BaseBindingActivity<ICreatePasscode.ViewModel>(),
    ICreatePasscode.View {

    companion object {
        fun newIntent(context: Context, isSettingPin: Boolean): Intent {
            val intent = Intent(context, CreatePasscodeActivity::class.java)
            intent.putExtra("isSettingPin", isSettingPin)
            return intent
        }
    }

    override fun getBindingVariable(): Int = BR.createPasscodeViewModel

    override fun getLayoutId(): Int = R.layout.activity_create_passcode

    override val viewModel: ICreatePasscode.ViewModel
        get() = ViewModelProviders.of(this).get(CreatePasscodeViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isSettingPin = intent.getValue(
            "isSettingPin",
            ExtraType.BOOLEAN.name
        ) as? Boolean
        viewModel.state.isSettingPin.set(isSettingPin ?: false)
        dialer.hideFingerprintView()
        viewModel.nextButtonPressEvent.observe(this, Observer {
            if (it == R.id.tvTermsAndConditions) {
                startFragment<WebViewFragment>(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        co.yap.yapcore.constants.Constants.PAGE_URL to URL_TERMS_CONDITION
                    ), showToolBar = true
                )
            } else
                setObservers()
        })
        preventTakeScreenShot(true)

    }

    override fun setObservers() {
        val intent = Intent()
        intent.putExtra("PASSCODE", viewModel.state.passcode)
        setResult(Constants.REQUEST_CODE_CREATE_PASSCODE, intent)
        finish()
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroy()
    }

    private val nextButtonObserver = Observer<Boolean> {
        val intent = Intent()
        intent.putExtra("PASSCODE", viewModel.state.passcode)
        setResult(Constants.REQUEST_CODE_CREATE_PASSCODE, intent)
        finish()
    }

    override fun onBackPressed() {
    }
}
