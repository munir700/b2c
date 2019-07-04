package co.yap.modules.onboarding.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.navOptions
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.viewmodels.CongratulationsViewModel
import co.yap.modules.onboarding.viewmodels.MobileViewModel
import co.yap.yapcore.BaseBindingFragment


class CongratulationsFragment : BaseBindingFragment<ICongratulations.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_onboarding_congratulations

    override val viewModel: ICongratulations.ViewModel
        get() = ViewModelProviders.of(this).get(CongratulationsViewModel::class.java)



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.ibanNumber = "AE07 0331 2345 6789 01** ***"
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