package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.viewmodels.MobileViewModel
import co.yap.widgets.mobile.CountryCodePicker
import kotlinx.android.synthetic.main.fragment_mobile.*


class MobileFragment : OnboardingChildFragment<IMobile.ViewModel>() {

    override fun getBindingVariable(): Int = BR.mobileViewModel
    override fun getLayoutId(): Int = R.layout.fragment_mobile

    override val viewModel: IMobile.ViewModel
        get() = ViewModelProviders.of(this).get(MobileViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.nextButtonPressEvent.observe(this, Observer {
            navigate(R.id.phoneVerificationFragment)
        })
        registerCarrierEditText()

    }


    private fun registerCarrierEditText() {
        val ccpLoadNumber: CountryCodePicker? = CountryCodePicker(this!!.context!!)
        ccpLoadNumber!!.registerCarrierNumberEditText(etMobileNumber)
//         ccpLoadNumber.setTypeFace()

        ccpLoadNumber.setPhoneNumberValidityChangeListener(object :
            CountryCodePicker.PhoneNumberValidityChangeListener {
            override fun onValidityChanged(isValidNumber: Boolean) {
                if (isValidNumber) {
                    Log.i("tvValidity", "Valid Number")
//                    imgValidity.setImageDrawable(resources.getDrawable(R.drawable.ic_assignment_turned_in_black_24dp))
//                    tvValidity.setText("Valid Number")
                    ccpContainer.setBackgroundResource(R.drawable.bg_round_edit_text)
                    etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.path, 0)
                    tvMobileError.visibility = View.GONE

//                    ccpContainer.isActivated=false
                } else {
                    Log.i("tvValidity", "Invalid Number")

                    ccpContainer.setBackgroundResource(R.drawable.bg_round_error_layout)

                    etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.invalid_name, 0)
//                    etMobileNumber.setCompoundDrawables(l,t,r,b)
//                    imgValidity.setImageDrawable(resources.getDrawable(R.drawable.ic_assignment_late_black_24dp))
//                    tvValidity
                    tvMobileError.setText("Invalid Number")
                    tvMobileError.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

}