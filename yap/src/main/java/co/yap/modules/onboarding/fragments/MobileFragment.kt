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
    override fun getBindingVariable(): Int = BR.viewModel
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

        ccpLoadNumber.setPhoneNumberValidityChangeListener(object :
            CountryCodePicker.PhoneNumberValidityChangeListener {
            override fun onValidityChanged(isValidNumber: Boolean) {
                if (isValidNumber) {
                    if (etMobileNumber.getText().trim().length >= 7) {
                        setErrorLayout()
//                        ccpContainer.setBackgroundResource(R.drawable.bg_round_error_layout)
//
//                        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.invalid_name, 0)
//                        tvMobileError.setText("Invalid Number")
                    } else if (etMobileNumber.text.length == 11) {
                        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.path, 0)
                        //enable btn blue
                        setNormalUI()

                    } else {
                        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        //disable btn grey
                    }
                    Log.i("tvValidity", "Valid Number")

//                    ccpContainer.isActivated = true
//                    ccpContainer.setBackgroundResource(R.drawable.bg_round_edit_text)
//                    tvMobileError.visibility = View.GONE
//                    ccpContainer.setPadding(
//                        resources.getDimensionPixelSize(R.dimen.margin_medium),
//                        0,
//                        resources.getDimensionPixelSize(R.dimen.margin_medium),
//                        0
//                    )
                    setNormalUI()

                } else {
//                    ccpContainer.isActivated = true
//                    ccpContainer.setBackgroundResource(R.drawable.bg_round_edit_text)
//                    ccpContainer.setPadding(
//                        resources.getDimensionPixelSize(R.dimen.margin_medium),
//                        0,
//                        resources.getDimensionPixelSize(R.dimen.margin_medium),
//                        0
//                    )
//                    etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    setNormalUI()
                    Log.i("tvValidity", "Invalid Number" + etMobileNumber.text.trim().length.toString())

                    if (etMobileNumber.getText().trim().length >= 7) {
                        Log.i("tvValidity", "Invalid")
                        Log.i(
                            "tvValidity",
                            etMobileNumber.text.trim().toString() + "   " + etMobileNumber.text.trim().length.toString()
                        )
                        Log.i(
                            "tvValidity",
                            etMobileNumber.text.toString() + "   " + etMobileNumber.text.length.toString()
                        )

                        setErrorLayout()
                    } else {
                        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)

                        Log.i(
                            "tvValidity",
                            "else" + etMobileNumber.text.trim().toString() + "   " + etMobileNumber.text.trim().length.toString()
                        )
                        //

                        setNormalUI()
                    }

                }
            }
        })
    }

    private fun setErrorLayout() {
        ccpContainer.isActivated = true
        ccpContainer.setBackgroundResource(R.drawable.bg_round_error_layout)

        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.invalid_name, 0)
        tvMobileError.setText("Invalid Number")
        tvMobileError.visibility = View.VISIBLE
    }

    private fun setNormalUI() {
        tvMobileError.visibility = View.GONE
        ccpContainer.isActivated = true
        ccpContainer.setBackgroundResource(R.drawable.bg_round_edit_text)
        ccpContainer.setPadding(
            resources.getDimensionPixelSize(R.dimen.margin_medium),
            0,
            resources.getDimensionPixelSize(R.dimen.margin_medium),
            0
        )
        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

}