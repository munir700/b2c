package co.yap.widgets.bottomsheet.roundtickselectionbottomsheet

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.widgets.bottomsheet.CoreBottomSheetAdapter
import co.yap.widgets.bottomsheet.CoreBottomSheetData
import co.yap.widgets.bottomsheet.CoreBottomSheetItemViewModel
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.ItemMultiSelectionBottomSheetWithFlagBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class RoundTickSelectionBottomSheetAdapter(
    private val list: MutableList<CoreBottomSheetData>,
    private val viewType: Int = Constants.VIEW_WITHOUT_FLAG
) : CoreBottomSheetAdapter(list, viewType) {

    override fun getLayoutIdForViewType(viewType: Int): Int =
        R.layout.item_multi_selection_bottom_sheet_with_flag

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return BottomSheetViewHolder(binding as ItemMultiSelectionBottomSheetWithFlagBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BottomSheetViewHolder).onBind(list[position], position, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun filterItem(constraint: CharSequence?, item: CoreBottomSheetData): Boolean {
        val filterString = constraint.toString().toLowerCase()
        val name = item.subTitle?.toLowerCase() ?: ""
        return name.contains(filterString)
    }
}

class BottomSheetViewHolder(private val itemMultiSelectionBinding: ItemMultiSelectionBottomSheetWithFlagBinding) :
    RecyclerView.ViewHolder(itemMultiSelectionBinding.root) {
    fun onBind(
        bottomSheetItem: CoreBottomSheetData,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemMultiSelectionBinding.viewModel =
            CoreBottomSheetItemViewModel(
                bottomSheetItem = bottomSheetItem,
                position = position,
                onItemClickListener = onItemClickListener
            )
        itemMultiSelectionBinding.executePendingBindings()
    }

}
