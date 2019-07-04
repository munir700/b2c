package co.yap.modules.onboarding.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.ICreatePasscode
import co.yap.modules.onboarding.viewmodels.CreatePasscodeViewModel
import co.yap.yapcore.BaseBindingActivity
import kotlinx.android.synthetic.main.screen_create_passcode.*

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
        viewModel.state.passcode = dialer.getText()
    }
}
