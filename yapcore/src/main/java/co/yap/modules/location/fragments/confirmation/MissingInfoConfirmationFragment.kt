package co.yap.modules.location.fragments.confirmation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.BR
import co.yap.yapcore.constants.Constants

class MissingInfoConfirmationFragment : BaseBindingFragment<IMissingInfoConfirmation.ViewModel>(),
    IMissingInfoConfirmation.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo_confirmation

    override val viewModel: IMissingInfoConfirmation.ViewModel
        get() = ViewModelProvider(this).get(MissingInfoConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onClickEvent.observe(viewLifecycleOwner, onClickView)
    }

    private fun initArguments() {
        arguments?.let { bundle ->
            viewModel.state.subTitle.set(bundle.getString(Constants.CONFIRMATION_DESCRIPTION))
            viewModel.state.missingInfoMap =
                bundle.getSerializable(Constants.KYC_AMENDMENT_MAP) as? HashMap<String?, List<String>?>
        }
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnDone -> {
                setIntentResult()
            }
        }
    }

    private fun setIntentResult() {
        val intent = Intent()
        intent.putExtra(Constants.KYC_AMENDMENT_SUCCESS, true)
        activity?.setResult(Activity.RESULT_OK, intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onClickEvent.removeObserver(onClickView)
    }

}