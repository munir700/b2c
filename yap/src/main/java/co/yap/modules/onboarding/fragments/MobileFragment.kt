package co.yap.modules.onboarding.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentMobileBinding
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.viewmodels.MobileViewModel
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.OnItemClickListener
import com.yap.core.extensions.finishAffinity
import com.yap.ghana.ui.onboarding.main.YapGhanaMainActivity
import com.yap.yappakistan.ui.onboarding.main.YapPkMainActivity


class MobileFragment : OnboardingChildFragment<IMobile.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_mobile

    override val viewModel: IMobile.ViewModel
        get() = ViewModelProvider(this).get(MobileViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /* viewModel.nextButtonPressEvent.observe(this, Observer {
             navigate(R.id.phoneVerificationFragment)
         })*/
        // viewModel.getCcp(etMobileNumber)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.requestDefaultFocus()
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)
        setTouchListener()

        viewModel.userVerified.observe(viewLifecycleOwner, Observer {
            val mobile = viewModel.state.mobile.replace(" ", "")
            if (it == "+92") {
                launchActivity<YapPkMainActivity>() {
                    putExtra("countryCode", viewModel.state.countryCode.get())
                    putExtra("mobileNo", mobile)
                }
            } else if (it == "+233") {
                launchActivity<YapGhanaMainActivity> {
                    putExtra("countryCode", viewModel.state.countryCode.get())
                    putExtra("mobileNo", mobile)
                }
            }
        })

    }

    private val clickListenerHandler = Observer<Int> { id ->
        when (id) {
            R.id.next_button -> {
                if (getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.prefixText != "+971")
                    viewModel.verifyUser(
                        getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.prefixText.toString(),
                        viewModel.state.mobile.replace(" ","")
                    )
                else {
                    viewModel.createOtp { success ->
                        if (success) {
                            navigate(R.id.phoneVerificationFragment)
                        }
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObserver(clickListenerHandler)
        //  viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }

    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (data is CoreBottomSheetData) {
                getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.setStartIconDrawable(
                    requireContext().getDropDownIconByName(
                        data.key ?: "AE"
                    )
                )
                viewModel.state.mobile = ""
                viewModel.state.countryCode.set(data.content.toString())
                getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.requestFocusForField()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        getDataBindingView<FragmentMobileBinding>().etMobileNumber.setTouchListener {
            getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.hideKeyboard()
            requireActivity().launchBottomSheetForMutlipleCountries(selectCountryItemClickListener)

        }
    }
}