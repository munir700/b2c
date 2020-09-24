package co.yap.modules.dashboard.store.young.pincode

import android.app.Activity
import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungCreatePincodeBinding
import co.yap.modules.location.activities.LocationSelectionActivity
import co.yap.networking.cards.responsedtos.Address
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import kotlinx.android.synthetic.main.fragment_young_create_pincode.*

class YoungCreatePinCodeFragment :
    BaseNavViewModelFragment<FragmentYoungCreatePincodeBinding, IYoungPinCode.State, YoungCreatePinCodeVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_create_pincode
    override fun toolBarVisibility() = false
    override fun setDisplayHomeAsUpEnabled() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
        youngDialer.hideFingerprintView()
        youngDialer.showDialerPassCodeView = false
        youngDialer.upDatedDialerPad(viewModel.state.passCode.value.toString())
        youngDialer.setInPutEditText(etPinCode)
    }

    override fun onClick(id: Int) {
        launchAddressSelection(true)
    }

    private fun launchAddressSelection(gotoNext: Boolean) {
        launchActivityForResult<LocationSelectionActivity>(init = {
            putExtra(LocationSelectionActivity.HEADING, "Your Card is ready to be sent out!")
            putExtra(
                LocationSelectionActivity.SUB_HEADING,
                "Make sure you are available at the below address"
            )
            putExtra(Constants.ADDRESS, state.address?.value ?: Address())
            putExtra(LocationSelectionActivity.IS_ON_BOARDING, false)
        }, completionHandler = { resultCode, data ->
            if (resultCode == Activity.RESULT_OK) {
                data?.getParcelableExtra<Address>(Constants.ADDRESS)?.apply {
                    state.address?.value = this
                }
                val success =
                    data?.getValue(Constants.ADDRESS_SUCCESS, ExtraType.BOOLEAN.name) as? Boolean
                state.address?.value?.let { selectedAddress ->
                    success?.let { success ->
                        if (success && gotoNext) {
                            navigate(YoungCreatePinCodeFragmentDirections.actionYoungCreatePinCodeFragmentToYoungCardSuccessFragment2())
                            //selectedAddress.designCode = viewModel.state.designCode
//                            viewModel.orderHouseHoldPhysicalCardRequest(selectedAddress) {
//                                if (it) {
//                                    navigateForward(
//                                        HHOnBoardingCardSelectionFragmentDirections.toKycSuccessFragment(),
//                                        arguments
//                                    )
//                                }
//                            }
                        }
                    }
                }
            }
        })
    }
}