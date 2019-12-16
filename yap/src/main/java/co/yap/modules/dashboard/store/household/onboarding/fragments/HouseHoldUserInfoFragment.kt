package co.yap.modules.dashboard.store.household.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldUserInfo
import co.yap.modules.dashboard.store.household.onboarding.viewmodels.HouseHoldUserInfoViewModel
import co.yap.translation.Translator

class HouseHoldUserInfoFragment : BaseOnBoardingFragment<IHouseHoldUserInfo.ViewModel>(),
    IHouseHoldUserInfo.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_user_info

    override val viewModel: IHouseHoldUserInfo.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldUserInfoViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
getString(Translator.getString())
        viewModel.clickEvent.observe(this, Observer {
            when (it) {

                R.id.tvEditPhoneNumber -> {
                    findNavController().navigate(R.id.action_personalDetailsFragment_to_change_phone_number_navigation)
                }

            }
        })

    }


    override fun onPause() {
        super.onPause()
        viewModel.clickEvent.removeObservers(this)

    }

    override fun onDestroy() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroy()

    }
}