package co.yap.widgets.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.LayoutBottomSheetBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.getScreenHeight
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet.*


open class CoreBottomSheet(
    private val mListener: OnItemClickListener?,
    private val bottomSheetItems: MutableList<CoreBottomSheetData>,
    private val viewType: Int = Constants.VIEW_WITHOUT_FLAG,
    private val configuration: BottomSheetConfiguration,
    private val iAnimationComplete: IAnimationComplete? = null,
    private val buttonClickListener: View.OnClickListener? = null
) : BottomSheetDialogFragment(), ICoreBottomSheet.View, IAnimationComplete {
    lateinit var viewDataBinding: LayoutBottomSheetBinding
    override val viewModel: CoreBottomSheetViewModel by viewModels()

    open val adapter: CoreBottomSheetAdapter by lazy {
        CoreBottomSheetAdapter(bottomSheetItems, viewType, this)
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        // DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        if (viewType == Constants.VIEW_ITEM_CARD_SUCCESSS) {
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.setCancelable(false)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewDataBinding.executePendingBindings()
        val adapter = CoreBottomSheetAdapter(bottomSheetItems, viewType, this)
        adapter.onItemClickListener = myListener
        adapter.allowFullItemClickListener = true
        viewModel.state.searchBarVisibility.set(configuration.showSearch)
        viewModel.state.headerSeparatorVisibility.set(configuration.showHeaderSeparator ?: false)
        configuration.heading?.let {
            viewDataBinding.tvlabel.text = it
            if (viewType == Constants.VIEW_ITEM_ACCOUNT_DETAIL) {
                viewDataBinding.tvlabel.gravity = Gravity.CENTER
                viewModel.state.buttonVisibility.set(true)
            }
        }
        viewDataBinding.lySearchView.etSearch.afterTextChanged {
            adapter.filter.filter(it) { itemCount ->
                if (itemCount == 0) {
                    viewModel.state.noItemFound.set(true)
                } else {
                    viewModel.state.noItemFound.set(false)
                }
            }
        }
        viewDataBinding.rvBottomSheet.layoutManager = LinearLayoutManager(context)
        val params = viewDataBinding.rvBottomSheet.layoutParams as ConstraintLayout.LayoutParams
        params.height =
            if (viewType == Constants.VIEW_WITH_FLAG || viewType == Constants.VIEW_FIXED_HEIGHT) (getScreenHeight() / 2) + 100 else params.height
        viewDataBinding.rvBottomSheet.layoutParams = params
        viewDataBinding.rvBottomSheet.adapter = adapter
        viewDataBinding.btnShare.setOnClickListener(buttonClickListener)

    }

    private val myListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            if (viewType != Constants.VIEW_ITEM_ACCOUNT_DETAIL)
                this@CoreBottomSheet.dismiss()
            mListener?.onItemClick(view, data, pos)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog ->
            bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            if (viewType == Constants.VIEW_ITEM_CARD_SUCCESSS) {
                bottomSheetDialog.setCanceledOnTouchOutside(false)
                bottomSheetDialog.behavior.isHideable = false
            }
        }
        return bottomSheetDialog
    }

    override fun showLoader(isVisible: Boolean) {
    }

    override fun showToast(msg: String) {
    }

    override fun showInternetSnack(isVisible: Boolean) {
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return true
    }

    override fun requestPermissions() {
    }

    override fun getString(resourceKey: String): String {
        return ""
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
    }

    override fun getScreenName(): String? = null

    override fun onAnimationComplete(isComplete: Boolean) {
        if (isComplete) {
            iAnimationComplete?.onAnimationComplete(isComplete)
        }
    }
}