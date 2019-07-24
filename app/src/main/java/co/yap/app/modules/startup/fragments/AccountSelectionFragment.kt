package co.yap.app.modules.startup.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import co.yap.app.R
import co.yap.modules.onboarding.activities.EidInfoReviewActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.defaults.DefaultViewModel
import co.yap.yapcore.defaults.IDefault
import kotlinx.android.synthetic.main.activity_account_selection.*

class AccountSelectionFragment : BaseBindingFragment<IDefault.ViewModel>(), IDefault.View {
    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(DefaultViewModel::class.java)

    override fun getBindingVariable(): Int = 0

    override fun getLayoutId(): Int = R.layout.activity_account_selection

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBusiness.setOnClickListener { }
        btnPersonal.setOnClickListener {
            //startActivity(context?.let { it1 -> EidInfoReviewActivity.newIntent(context = it1) })
            findNavController().navigate(R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)
            /* Navigation.createNavigateOnClickListener(
                    R.id.action_KYCHomeFragment_to_eidInfoReviewFragment)*/
            /*           Navigation.createNavigateOnClickListener(
                           R.id.action_accountSelectionFragment_to_welcomeFragment,
                           Bundle().apply { putSerializable(getString(R.string.arg_account_type), AccountType.B2C_ACCOUNT) })*/
        }
        tvSignIn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_accountSelectionFragment_to_loginFragment))
    }
}