package co.yap.widgets.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.LayoutBottomSheetBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CoreBottomSheet(
    private val mListener: OnItemClickListener,
    private val bottomSheetItems: MutableList<CoreBottomSheetData>,
    private val headingLabel: String? = null,
    private val viewType: Int = Constants.VIEW_WITHOUT_FLAG
) : BottomSheetDialogFragment(), ICoreBottomSheet.View {
    lateinit var viewDataBinding: ViewDataBinding
    override val viewModel: CoreBottomSheetViewModel
        get() = ViewModelProviders.of(this).get(CoreBottomSheetViewModel::class.java)

    val adapter: CoreBottomSheetAdapter by lazy {
        CoreBottomSheetAdapter(bottomSheetItems, viewType)
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_bottom_sheet, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewDataBinding.executePendingBindings()
        val adapter: CoreBottomSheetAdapter = CoreBottomSheetAdapter(bottomSheetItems, viewType)
        viewModel.state.searchBarVisibility.set(viewType != Constants.VIEW_WITHOUT_FLAG)
        headingLabel?.let {
            getBinding().tvlabel.text = it
        }
        getBinding().lySearchView.etSearch.afterTextChanged {
            adapter.filter.filter(it)
        }
        getBinding().rvBottomSheet.layoutManager = LinearLayoutManager(context)
        adapter.onItemClickListener = myListener
        adapter.allowFullItemClickListener = true
        getBinding().rvBottomSheet.adapter = adapter

    }

    private val myListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            mListener.onItemClick(view, this@CoreBottomSheet, pos)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog ->
            /* val d = dialog as BottomSheetDialog
             val bottomSheetInternal =
                 d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
             BottomSheetBehavior.from<View?>(bottomSheetInternal!!).state =
                 BottomSheetBehavior.STATE_EXPANDED*/

            val modalBottomSheetBehavior = bottomSheetDialog.behavior
            modalBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            modalBottomSheetBehavior.saveFlags = BottomSheetBehavior.SAVE_SKIP_COLLAPSED
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

    private fun getBinding() = viewDataBinding as LayoutBottomSheetBinding

}