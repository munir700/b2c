package co.yap.modules.location.tax

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentTaxInfoBinding
import co.yap.yapcore.helpers.extentions.makeLinks
import co.yap.yapcore.helpers.extentions.toast

class TaxInfoFragment : BaseBindingFragment<ITaxInfo.ViewModel>(), ITaxInfo.View {

    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_tax_info
    override val viewModel: ITaxInfo.ViewModel
        get() = ViewModelProviders.of(this).get(TaxInfoViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.countries =
            arguments?.getParcelableArrayList("countries")
        getBinding().tvTermsConditions.makeLinks(
            Pair("Individual Self Certification Form for CRS & FACTA.", View.OnClickListener {
                toast("api call")
            })
        )
    }

    override fun addObservers() {
        viewModel.clickEvent.observe(this, clickObserver)
    }

    private val clickObserver = Observer<Int> {
        when (it) {
            R.id.nextButton -> {
                viewModel.saveInfoDetails {
                    setIntentResult()
                }
            }
        }
    }

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
