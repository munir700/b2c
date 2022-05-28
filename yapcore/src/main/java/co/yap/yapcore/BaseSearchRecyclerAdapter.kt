package co.yap.yapcore

import android.widget.Filter
import android.widget.Filterable


abstract class BaseSearchRecyclerAdapter<T : Any, VM : BaseListItemViewModel<T>, VH : BaseViewHolder<T, VM>>(
    private val mDataList: MutableList<T>
) : BaseRVAdapter<T, VM, VH>(mDataList,null), Filterable {
    private var tempList: MutableList<T>? = null
    var onFilter: OnFilter<T>? = null

    init {
        //mDataList = mutableListOf()
        tempList = mutableListOf()
        tempList?.addAll(datas)
    }

    fun performFilter(text: CharSequence?): MutableList<T> {
        val result: MutableList<T> = mutableListOf()
        result.clear()
        tempList?.let {
            it.forEach { item ->
                if (onFilter?.onFilterApply(text, item) == true) {
                    result.add(item)
                }
            }
        }
        return result
    }

    open fun onSearch(text: CharSequence?, mFilter: OnFilter<T>?) {
        onFilter = mFilter
        filter.filter(text)
    }

    override fun getFilter() = filtered
    private val filtered: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filtered = performFilter(constraint.toString().lowercase())
            val filterResults = FilterResults()
            filterResults.count = filtered.size
            filterResults.values = filtered
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            datas.clear()
            results?.let {
                results.values?.let {
                    datas.addAll(results.values as Collection<T>)
                    onFilter?.onFilterResult(datas)
                    notifyDataSetChanged()
                }
            }

        }
    }

    override fun setData(newData: MutableList<T>) {
        super.setData(newData)
        tempList?.clear()
        tempList?.addAll(newData)
    }

    open fun addAll(datas: List<T>, deepCopy: Boolean = true) {
        super.addAll(datas)
        if (deepCopy) {
            tempList?.addAll(datas)
        }
    }

    override fun removeAll() {
        super.removeAll()
        tempList?.clear()
    }

    open fun clearFilter() {
        datas.clear()
        notifyDataSetChanged()
        tempList?.let { datas.addAll(it) }
        onFilter?.onFilterResult(datas)
    }
}