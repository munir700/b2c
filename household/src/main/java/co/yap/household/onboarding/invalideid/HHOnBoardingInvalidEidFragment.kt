package co.yap.household.onboarding.invalideid

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingInvalidEidBinding
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.livedata.LogOutLiveData
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.managers.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHOnBoardingInvalidEidFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingInvalidEidBinding, IHHOnBoardingInvalidEid.State, HHOnBoardingInvalidEidVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_invalid_eid
    override fun setDisplayHomeAsUpEnabled() = false
    override fun toolBarVisibility() = false
    override val viewModel: HHOnBoardingInvalidEidVM by viewModels()
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnLogout -> confirm(message = getString(R.string.screen_profile_settings_logout_display_text_alert_message),
                title = getString(R.string.screen_profile_settings_logout_display_text_alert_title),
                callback = {
                    LogOutLiveData.getInstance(requireContext())
                        .observe(this@HHOnBoardingInvalidEidFragment, Observer {
                            if (it) {
                                SessionManager.doLogout(requireContext())
                            }
                        })
                },
                negativeCallback = {})
        }
    }

}
