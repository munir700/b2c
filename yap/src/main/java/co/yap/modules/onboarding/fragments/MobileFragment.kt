package co.yap.modules.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.viewmodels.MobileViewModel
import co.yap.yapcore.helpers.extentions.launchBottomSheetForMutlipleCountries
import co.yap.yapcore.interfaces.OnItemClickListener
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
        viewModel.getCcp(etMobileNumber)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(viewLifecycleOwner, clickListenerHandler)

    }
    private val clickListenerHandler = Observer<Int> { id ->
        when (id) {
            R.id.ccpContainer -> activity?.let { context ->
                context.launchBottomSheetForMutlipleCountries(selectCountryItemClickListener, arrayListOf())
            }
        }
    }
    override fun onDestroyView() {
        viewModel.nextButtonPressEvent.removeObservers(this)
        super.onDestroyView()
    }
    private val selectCountryItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            showToast("Chal beta chutti kr!!")
        }
    }
}