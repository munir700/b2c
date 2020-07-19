package co.yap.household.onboarding.invalideid

import android.os.Bundle
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingInvalidEidBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.livedata.LogOutLiveData
import co.yap.yapcore.managers.MyUserManager

class HHOnBoardingInvalidEidFragment :
    BaseNavViewModelFragment<FragmentHhonBoardingInvalidEidBinding, IHHOnBoardingInvalidEid.State, HHOnBoardingInvalidEidVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_invalid_eid
    override fun setDisplayHomeAsUpEnabled() = false
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnLogout -> confirm(message = getString(R.string.screen_profile_settings_logout_display_text_alert_message),
                title = getString(R.string.screen_profile_settings_logout_display_text_alert_title),
                callback = {
                    LogOutLiveData.getInstance(requireContext())
                        .observe(this@HHOnBoardingInvalidEidFragment, Observer {
                            if (it) {
                                MyUserManager.doLogout(requireContext())
                            }
                        })
                },
                negativeCallback = {})
        }
    }

}
