package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.more.main.activities.MoreActivity
import co.yap.modules.dashboard.more.main.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IChangePhoneNumber
import co.yap.modules.dashboard.more.profile.viewmodels.ChangePhoneNumberViewModel
import co.yap.translation.Strings
import co.yap.yapcore.managers.MyUserManager
import kotlinx.android.synthetic.main.fragment_change_phone_number.*

class ChangePhoneNumberFragment : MoreBaseFragment<IChangePhoneNumber.ViewModel>(),
    IChangePhoneNumber.View {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_change_phone_number
    override val viewModel: IChangePhoneNumber.ViewModel
        get() = ViewModelProviders.of(this).get(ChangePhoneNumberViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.changePhoneNumberSuccessEvent.observe(this, Observer {
            MyUserManager.user?.currentCustomer?.mobileNo =viewModel.state.mobile
            MyUserManager.user?.currentCustomer?.countryCode = viewModel.state.countryCode
            val action =
                ChangePhoneNumberFragmentDirections.actionChangePhoneNumberFragmentToSuccessFragment(
                    getString(Strings.screen_phone_number_success_display_text_sub_heading),
                    MyUserManager.user?.currentCustomer?.getFormattedPhoneNumber(requireContext()).toString()
                )
            findNavController().navigate(action)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.state.countryCode = ccpSelector.getDefaultCountryCode()
        viewModel.state.etMobileNumber = etNewMobileNumber
        if (MoreActivity.navigationVariable) {
            MoreActivity.navigationVariable = false
            viewModel.changePhoneNumber()
        }

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}