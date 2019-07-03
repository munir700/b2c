package co.yap.modules.onboarding.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navOptions
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.viewmodels.EmailViewModel
import co.yap.yapcore.BaseBindingFragment


class EmailFragment : BaseBindingFragment<IEmail.ViewModel>() {

    override fun getBindingVariable(): Int = BR.emailViewModel
    override fun getLayoutId(): Int = R.layout.fragment_email

    override val viewModel: IEmail.ViewModel
        get() = ViewModelProviders.of(this).get(EmailViewModel::class.java)

    override fun getString(resourceKey: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }
    }
}