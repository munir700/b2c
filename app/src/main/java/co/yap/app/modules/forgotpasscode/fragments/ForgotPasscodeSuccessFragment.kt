package co.yap.app.modules.forgotpasscode.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.app.BR
import co.yap.app.R
import co.yap.app.activities.MainActivity
import co.yap.app.modules.forgotpasscode.interfaces.IForgotPasscodeSuccess
import co.yap.app.modules.forgotpasscode.viewmodels.ForgotPasscodeSuccessViewModel
import co.yap.yapcore.BaseBindingActivity
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.IBase
import co.yap.yapcore.helpers.SharedPreferenceManager

class ForgotPasscodeSuccessFragment : BaseBindingFragment<IForgotPasscodeSuccess.ViewModel>() {
    private lateinit var sharedPreferenceManager: SharedPreferenceManager

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_forgot_passcode_success

    override val viewModel: IForgotPasscodeSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(ForgotPasscodeSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferenceManager = SharedPreferenceManager(requireContext())

        viewModel.handlePressOnButtonEvent.observe(this, Observer {
            sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, false)
            val intent=Intent(context,MainActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.flags=Intent.FLAG_ACTIVITY_NO_HISTORY
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            activity?.finish()
           // findNavController().popBackStack(R.id.loginFragment,false)
        })
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}