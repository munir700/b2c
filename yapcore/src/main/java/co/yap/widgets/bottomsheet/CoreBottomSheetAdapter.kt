package co.yap.widgets.bottomsheet

import android.text.TextUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.countryutils.country.Country
import co.yap.yapcore.BaseBindingRecyclerAdapter
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.databinding.ItemBottomsheetWithFlagBinding
import co.yap.yapcore.databinding.ItemCityBinding
import co.yap.yapcore.interfaces.OnItemClickListener

class CoreBottomSheetAdapter(
    private val list: MutableList<CoreBottomSheetData>,
    private val viewType: Int = Constants.VIEW_WITHOUT_FLAG
) : BaseBindingRecyclerAdapter<CoreBottomSheetData, RecyclerView.ViewHolder>(list), Filterable {

    private var mFilter: FilteredList? = null
     var mOriginalList: ArrayList<CoreBottomSheetData> = arrayListOf()
     var mSortedList: ArrayList<CoreBottomSheetData> = arrayListOf()

    init {
        mOriginalList = list as ArrayList<CoreBottomSheetData>
        mSortedList = mOriginalList
    }


    override fun getLayoutIdForViewType(viewType: Int): Int =
        if (viewType == Constants.VIEW_WITH_FLAG) R.layout.item_bottomsheet_with_flag else R.layout.item_city

    override fun onCreateViewHolder(binding: ViewDataBinding): RecyclerView.ViewHolder {
        return when (binding) {
            is ItemBottomsheetWithFlagBinding -> {
                BottomSheetWithFlagViewHolder(binding)
            }
            is ItemCityBinding -> {
                BottomSheetViewHolder(binding)
            }
            else -> {
                BottomSheetViewHolder(binding as ItemCityBinding)

            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BottomSheetViewHolder -> {
                holder.onBind(list[position], position, onItemClickListener)
            }
            is BottomSheetWithFlagViewHolder -> {
                holder.onBind(list[position], position, onItemClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun getFilter(): Filter {
        if (mFilter == null) {
            mFilter = FilteredList()
        }
        return mFilter ?: FilteredList()
    }
    inner class FilteredList : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (TextUtils.isEmpty(constraint)) {
                filterResults.count = mSortedList.size
                filterResults.values = mSortedList
                return filterResults
            }
            val filterStrings = java.util.ArrayList<CoreBottomSheetData>()
            for (Item in mSortedList) {
                if (Item.subTitle?.toLowerCase()?.contains(constraint.toString().toLowerCase()) == true || Item.content?.toLowerCase()?.contains(constraint.toString().toLowerCase()) == true) {
                    filterStrings.add(Item)
                }
            }
            filterResults.count = filterStrings.size
            filterResults.values = filterStrings
            return filterResults

        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mOriginalList = results?.values as ArrayList<CoreBottomSheetData>
            if (constraint != null) {
                notifyDataSetChanged()
            }
        }

    }
}

class BottomSheetViewHolder(private val itemCityBinding: ItemCityBinding) :
    RecyclerView.ViewHolder(itemCityBinding.root) {
    fun onBind(
        bottomSheetItem: CoreBottomSheetData,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemCityBinding.viewModel =
            CoreBottomSheetItemViewModel(
                bottomSheetItem = bottomSheetItem,
                position = position,
                onItemClickListener = onItemClickListener
            )
        itemCityBinding.executePendingBindings()
    }

}

class BottomSheetWithFlagViewHolder(private val itemFlagBinding: ItemBottomsheetWithFlagBinding) :
    RecyclerView.ViewHolder(itemFlagBinding.root) {
    fun onBind(
        bottomSheetItem: CoreBottomSheetData,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        itemFlagBinding.viewModel = CoreBottomSheetItemViewModel(
            bottomSheetItem = bottomSheetItem,
            position = position,
            onItemClickListener = onItemClickListener
        )
        itemFlagBinding.executePendingBindings()
    }
}
