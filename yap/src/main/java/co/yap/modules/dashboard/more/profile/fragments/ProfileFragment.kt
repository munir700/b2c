package co.yap.modules.dashboard.more.profile.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.cards.addpaymentcard.viewmodels.AddPaymentCardViewModel
import co.yap.modules.dashboard.more.fragments.MoreBaseFragment
import co.yap.modules.dashboard.more.profile.intefaces.IProfile
import co.yap.modules.dashboard.more.profile.viewmodels.ProfileViewModel
import co.yap.yapcore.helpers.SharedPreferenceManager

class ProfileFragment : MoreBaseFragment<IProfile.ViewModel>(), IProfile.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override val viewModel: IProfile.ViewModel
        get() = ViewModelProviders.of(this).get(ProfileViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SharedPreferenceManager(context!!).removeValue(SharedPreferenceManager.KEY_AVAILABLE_BALANCE)

        activity?.let {
            ViewModelProviders.of(it).get(AddPaymentCardViewModel::class.java)
                .state.tootlBarTitle = "Profile"
        }


    }

    override fun onPause() {
//        viewModel.clickEvent.removeObservers(this)
        super.onPause()

    }

    override fun onDestroy() {
//        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()
    }
}