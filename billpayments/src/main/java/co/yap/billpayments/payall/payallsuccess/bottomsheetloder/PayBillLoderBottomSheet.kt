package co.yap.billpayments.payall.payallsuccess.bottomsheetloder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import co.yap.billpayments.R
import co.yap.billpayments.databinding.BottomSheetPayAllBillsLoadingBinding
import co.yap.yapcore.BR
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PayBillLoderBottomSheet : BottomSheetDialogFragment(), IPayBillLoderBottomSheet.View{

    lateinit var viewDataBinding: ViewDataBinding
    override val viewModel: PayBillLoderBottomSheetViewModel by viewModels()
    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_pay_all_bills_loading, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewDataBinding.executePendingBindings()
    }

    private fun getBinding() = viewDataBinding as BottomSheetPayAllBillsLoadingBinding

    override fun showLoader(isVisible: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showToast(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showInternetSnack(isVisible: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isPermissionGranted(permission: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun requestPermissions() {
        TODO("Not yet implemented")
    }

    override fun getString(resourceKey: String): String {
        TODO("Not yet implemented")
    }

    override fun getScreenName(): String? {
        TODO("Not yet implemented")
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
        TODO("Not yet implemented")
    }
}
