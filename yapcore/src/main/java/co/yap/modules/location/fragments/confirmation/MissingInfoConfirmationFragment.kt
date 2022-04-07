package co.yap.modules.location.fragments.confirmation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.yap.yapcore.BR
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.FragmentMissinginfoConfirmationBinding
import co.yap.yapcore.managers.SessionManager

class MissingInfoConfirmationFragment :
    BaseBindingFragment<FragmentMissinginfoConfirmationBinding, IMissingInfoConfirmation.ViewModel>(),
    IMissingInfoConfirmation.View {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_missinginfo_confirmation

    override val viewModel: IMissingInfoConfirmation.ViewModel
        get() = ViewModelProvider(this).get(MissingInfoConfirmationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArguments()
        requireActivity().onBackPressedDispatcher.addCallback { }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onClickEvent.observe(viewLifecycleOwner, onClickView)
    }

    private fun initArguments() {
        arguments?.let { bundle ->
            val descData =
                bundle.getSerializable(Constants.CONFIRMATION_DESCRIPTION) as? Pair<String, String>
            descData?.let {
                viewModel.state.title.set(it.first)
                viewModel.state.subTitle.set(it.second)
            }
            viewModel.state.missingInfoMap =
                bundle.getSerializable(Constants.KYC_AMENDMENT_MAP) as? HashMap<String?, List<String>?>
        }
    }

    private val onClickView = Observer<Int> {
        when (it) {
            R.id.btnDone -> {
                if (viewModel.state.missingInfoMap?.size == 1) {
                    SessionManager.getAccountInfo()
                    SessionManager.onAccountInfoSuccess.observe(
                        viewLifecycleOwner,
                        Observer { isSuccess ->
                            if (isSuccess) {
                                setIntentResult()
                            }
                        })
                } else {
                    setIntentResult()
                }
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