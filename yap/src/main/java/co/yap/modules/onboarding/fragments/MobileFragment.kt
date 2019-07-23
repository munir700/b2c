package co.yap.modules.onboarding.fragments

import android.os.Bundle
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

                    if (etMobileNumber.text.length == 11) {
                        //enable btn blue
                        setNormalUI()
                        etMobileNumber.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.path, 0)

                    } else {
                        setNormalUI()

                        //disable btn grey
                    }
//                    setNormalUI()
                } else {
                    setNormalUI()
                    if (etMobileNumber.getText().toString().replace(" ", "").trim().length >= 9) {
                        setErrorLayout()
                    } else {
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