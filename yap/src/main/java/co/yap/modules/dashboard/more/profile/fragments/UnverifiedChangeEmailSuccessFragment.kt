package co.yap.modules.dashboard.more.profile.fragments

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.profile.intefaces.IUnverifiedChangeEmailSuccess
import co.yap.modules.dashboard.more.profile.viewmodels.UnverifiedChangeEmailSuccessViewModel
import co.yap.yapcore.BaseBindingFragment

class UnverifiedChangeEmailSuccessFragment :
    BaseBindingFragment<IUnverifiedChangeEmailSuccess.ViewModel>(),
    IUnverifiedChangeEmailSuccess.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_unverified_change_email_success

    override val viewModel: IUnverifiedChangeEmailSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(UnverifiedChangeEmailSuccessViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.mailButtonClickEvent.observe(this, Observer {
            when (it) {
                R.id.btnOpenMailApp -> {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                    startActivity(Intent.createChooser(intent, "Choose"))
                }
                R.id.tvGoToDashboard -> {
                    activity?.finish()
                }
            }

        })
    }

}