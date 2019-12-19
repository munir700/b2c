package co.yap.modules.dashboard.store.household.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.modules.dashboard.store.household.onboarding.interfaces.IHouseHoldSuccess
import co.yap.modules.dashboard.store.household.onboarding.viewmodels.HouseHoldSuccessViewModel

class HouseHoldSuccessFragment : BaseOnBoardingFragment<IHouseHoldSuccess.ViewModel>(),
    IHouseHoldSuccess.View {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_success

    override val viewModel: IHouseHoldSuccess.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldSuccessViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()


        viewModel.clickEvent.observe(this, Observer {
            when (it) {
                R.id.btnGoToHouseHold -> {

                    findNavController().navigate(R.id.action_houseHoldSuccessFragment_to_yapDashboardActivity)
                    activity!!.finish()

                 }

                R.id.btnShare -> {
                    shareInfo()
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

    private fun shareInfo() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getBody())
        startActivity(Intent.createChooser(sharingIntent, "Share"))
    }

    private fun getBody(): String {
        return "Email:\n ${viewModel.state.houseHoldUserEmail}\n" +
                "PassCode:\n ${viewModel.state.houseHoldUserPassCode}"
    }

}