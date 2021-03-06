package co.yap.app.modules.phoneverification

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.BR
import co.yap.app.R
import co.yap.app.databinding.FragmentMobileBinding
import co.yap.app.main.MainChildFragment
import co.yap.modules.onboarding.models.CountryCode
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.ExtraKeys
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.extentions.*
import co.yap.yapcore.interfaces.OnItemClickListener
import com.yap.ghana.ui.onboarding.main.YapGhanaMainActivity
import com.yap.yappakistan.ui.onboarding.main.YapPkMainActivity

class MobileFragment : MainChildFragment<FragmentMobileBinding, IMobile.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_mobile

    override val viewModel: MobileViewModel
        get() = ViewModelProvider(this).get(MobileViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataBindingView<FragmentMobileBinding>().lifecycleOwner = this
        getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.requestFocusForField()
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)
        setTouchListener()
        viewModel.userVerified.observe(viewLifecycleOwner, Observer {
            val mobile = viewModel.state.mobile.replace(" ", "")
            SharedPreferenceManager.getInstance(requireContext())
                .save(Constants.KEY_COUNTRY_CODE, it)
            SharedPreferenceManager.getInstance(requireContext())
                .save(Constants.KEY_MOBILE_NO, mobile ?: "")
            when (it) {
                CountryCode.GHANA.countryCode -> {
                    launchActivity<YapGhanaMainActivity>() {
                        putExtra("countryCode", viewModel.state.countryCode.value)
                        putExtra("mobileNo", mobile)
                        putExtra("startOnboardingTime", viewModel.onboardingData.startTime?.time)
                    }
                }
                CountryCode.PAK.countryCode -> {
                    launchActivity<YapPkMainActivity> {
                        putExtra("countryCode", viewModel.state.countryCode.value)
                        putExtra("mobileNo", mobile)
                        putExtra("startOnboardingTime", viewModel.onboardingData.startTime?.time)
                    }
                }
            }
        })
        viewModel.countriesList.observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                requireActivity().launchBottomSheetForMutlipleCountries(
                    list,
                    selectCountryItemClickListener
                )
            }
        })

    }

    private val clickListenerHandler = Observer<Int> { id ->
        when (id) {
            R.id.next_button -> {
                if (getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.prefixText != CountryCode.UAE.countryCode)
                    viewModel.verifyUser(
                        getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.prefixText.toString(),
                        viewModel.state.mobile.replace(" ", "")
                    )
                else {
                    viewModel.createOtp { success ->
                        if (success) {
                            viewModel.onboardingData.accountType = AccountType.B2C_ACCOUNT
                            navigate(
                                R.id.action_mobileFragment2_to_onboardingActivity, bundleOf(
                                    ExtraKeys.ON_BOARDING_DATA.name to viewModel.onboardingData
                                )
                            )
                        }
                    }
                }
            }
            R.id.tbBtnBack -> {
                navigateBack()
            }

        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObserver(clickListenerHandler)
        viewModel.countriesList.removeObservers(viewLifecycleOwner)
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
                viewModel.state.countryCode.value = data.content.toString()
                viewModel.state.error = ""
                viewModel.state.mobileError = ""
                getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.requestFocusForField()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener() {
        getDataBindingView<FragmentMobileBinding>().etMobileNumber.setTouchListener {
            getDataBindingView<FragmentMobileBinding>().tlPhoneNumber.hideKeyboard()
            val list = SharedPreferenceManager.getInstance(requireContext())
                .getValueString(Constants.KEY_COUNTRIES_LIST)?.jsonToList()
            if (list.isNullOrEmpty().not()) {
                viewModel.countriesList.value = list
            }
        }
    }
}