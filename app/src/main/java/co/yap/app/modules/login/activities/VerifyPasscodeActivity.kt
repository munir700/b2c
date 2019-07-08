package co.yap.app.modules.login.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.modules.login.interfaces.IVerifyPasscode
import co.yap.app.modules.login.viewmodels.VerifyPasscodeViewModel
import co.yap.yapcore.BaseBindingActivity

class VerifyPasscodeActivity : BaseBindingActivity<IVerifyPasscode.ViewModel>() {

    companion object {
        fun newIntent(mContext: Context): Intent = Intent(mContext, VerifyPasscodeActivity::class.java)
    }

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_verify_passcode

    override val viewModel: IVerifyPasscode.ViewModel
        get() = ViewModelProviders.of(this).get(VerifyPasscodeViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.signInButtonPressEvent.observe(this, signInButtonObserver)
    }


    private val signInButtonObserver = Observer<Boolean> {
       showToast("Yoo")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.signInButtonPressEvent.removeObserver(signInButtonObserver)
    }
}
