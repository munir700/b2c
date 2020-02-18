package co.yap.modules.kyc.fragments

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.BR
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsResponse
import co.yap.modules.kyc.viewmodels.InformationErrorViewModel
import co.yap.modules.onboarding.interfaces.IInformationError
import co.yap.translation.Strings
import co.yap.translation.Translator

class InformationErrorFragment : KYCChildFragment<IInformationError.ViewModel>() {
    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_information_error
    override val viewModel: InformationErrorViewModel
        get() = ViewModelProviders.of(this).get(InformationErrorViewModel::class.java)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val countryName =
            arguments?.let { InformationErrorFragmentArgs.fromBundle(it).countryName }.toString()
        viewModel.countryName = Translator.getString(
            requireContext(),
            Strings.screen_kyc_information_error_display_text_title_from_usa, countryName
        )

        viewModel.clickEvent.observe(this, Observer {
            viewModel.parentViewModel?.finishKyc?.value = DocumentsResponse(false)
        })
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }

    override fun onBackPressed(): Boolean = true

}