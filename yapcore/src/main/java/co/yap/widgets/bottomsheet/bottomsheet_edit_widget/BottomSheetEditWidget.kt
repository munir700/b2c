package co.yap.widgets.bottomsheet.bottomsheet_edit_widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.R
import co.yap.yapcore.databinding.LayoutDashboardBottomSheetBindingImpl

class BottomSheetEditWidget(
    private val configuration: BottomSheetConfiguration,
    private val buttonClickListener: View.OnClickListener? = null
) : CoreBottomSheet(
    null,
    bottomSheetItems = mutableListOf(),
    configuration = configuration,
    buttonClickListener = buttonClickListener
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.layout_dashboard_bottom_sheet,
                container,
                false
            )
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDataAndViews()
    }

    private fun setDataAndViews() {
        getBinding().btnHide.setOnClickListener(buttonClickListener)
        getBinding().tvCancel.setOnClickListener(buttonClickListener)
        configuration.heading?.let {
            getBinding().tvHeading.text = it
        }
        configuration.subHeading?.let {
            getBinding().tvContent.text = it
        }
    }

    private fun getBinding() = viewDataBinding as LayoutDashboardBottomSheetBindingImpl

}