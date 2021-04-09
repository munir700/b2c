package co.yap.widgets.bottomsheet.roundtickselectionbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.yapcore.BR
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.LayoutRoundTickSelectionBottomSheetBinding
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.helpers.extentions.getScreenHeight
import co.yap.yapcore.interfaces.OnItemClickListener

class RoundTickSelectionBottomSheet(
    private val mListener: OnItemClickListener?,
    private val bottomSheetItems: MutableList<CoreBottomSheetData>,
    private val headingLabel: String? = null,
    private val viewType: Int = Constants.VIEW_WITHOUT_FLAG
) : CoreBottomSheet(mListener, bottomSheetItems, headingLabel, viewType) {
    override val adapter: RoundTickSelectionBottomSheetAdapter by lazy {
        RoundTickSelectionBottomSheetAdapter(bottomSheetItems, viewType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.layout_round_tick_selection_bottom_sheet,
                container,
                false
            )
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        viewDataBinding.setVariable(BR.viewModel, viewModel)
        viewDataBinding.executePendingBindings()
        adapter.onItemClickListener = myListener
        adapter.allowFullItemClickListener = true
        viewModel.state.searchBarVisibility.set(viewType != Constants.VIEW_WITHOUT_FLAG)
        headingLabel?.let {
            getBinding().tvlabel.text = it
        }
        getBinding().lySearchView.etSearch.afterTextChanged {
            adapter.filter.filter(it) { itemCount ->
                if (itemCount == 0) {
                    viewModel.state.noItemFound.set(true)
                } else {
                    viewModel.state.noItemFound.set(false)
                }
            }
        }
        getBinding().rvBottomSheet.layoutManager = LinearLayoutManager(context)
        val params = getBinding().rvBottomSheet.layoutParams as ConstraintLayout.LayoutParams
        params.height =
            if (viewType == Constants.VIEW_WITH_FLAG) (getScreenHeight() / 2) + 100 else params.height
        getBinding().rvBottomSheet.layoutParams = params
        getBinding().rvBottomSheet.adapter = adapter
    }

    private val myListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            this@RoundTickSelectionBottomSheet.dismiss()
            mListener?.onItemClick(view, data, pos)
        }
    }

    private fun getBinding() = viewDataBinding as LayoutRoundTickSelectionBottomSheetBinding
}
