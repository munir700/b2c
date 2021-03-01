package co.yap.modules.location.tax

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.countryutils.country.Country
import co.yap.modules.location.fragments.LocationChildFragment
import co.yap.modules.pdf.PDFActivity
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentTaxInfoBinding
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.extentions.launchBottomSheet
import co.yap.yapcore.helpers.extentions.makeLinks
import co.yap.yapcore.interfaces.OnItemClickListener

class TaxInfoFragment : LocationChildFragment<ITaxInfo.ViewModel>(), ITaxInfo.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_tax_info
    override val viewModel: TaxInfoViewModel
        get() = ViewModelProviders.of(this).get(TaxInfoViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBinding().tvTermsConditions.makeLinks(
            Pair("Individual Self Certification Form for CRS & FATCA.", View.OnClickListener {
                if (viewModel.state.valid.get() == true) {
                    viewModel.saveInfoDetails(false) { pdf ->
                        trackEventWithScreenName(FirebaseEvent.FATCA_KNOW_MORE)
                        startActivity(
                            PDFActivity.newIntent(view.context, pdf ?: "", true)
                        )
                    }
                }
            })
        )
    }


    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
        viewModel.state.viewState.observe(this, Observer {
            it?.let {
                when (it) {
                    is ITaxInfo.CountryPicker -> {
                        requireActivity().launchBottomSheet(
                            itemClickListener = object : OnItemClickListener {
                                override fun onItemClick(view: View, data: Any, pos: Int) {
                                    viewModel.onCountryPicked(
                                        it.view,
                                        data as Country,
                                        it.data as TaxModel,
                                        it.pos
                                    )
                                }
                            },
                            label = "Select Country",
                            viewType = Constants.VIEW_WITH_FLAG,
                            countriesList = viewModel.parentViewModel?.countries?.filter { country -> country.isoCountryCode2Digit != "AE" }
                        )
                    }
                    else -> {

                    }
                }
            }
        })
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                viewModel.saveInfoDetails(true) {
                    trackEventWithScreenName(FirebaseEvent.TAX_RESIDENCE_SUBMIT)
                    setIntentResult()
                }
            }
            R.id.ivBackBtn -> {
                activity?.onBackPressed()
            }
        }
    }

    override fun onBackPressed(): Boolean = false

    override fun removeObservers() {
        viewModel.clickEvent.removeObserver(clickObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeObservers()
    }

    override fun getBinding(): FragmentTaxInfoBinding {
        return (viewDataBinding as FragmentTaxInfoBinding)
    }

    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra(Constants.ADDRESS_SUCCESS, true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }
}
