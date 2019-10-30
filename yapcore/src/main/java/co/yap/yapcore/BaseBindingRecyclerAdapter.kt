package co.yap.yapcore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.interfaces.OnItemClickListener

abstract class BaseBindingRecyclerAdapter<T : Any, VH : RecyclerView.ViewHolder>(private val list: MutableList<T>) :
    RecyclerView.Adapter<VH>() {

    var onItemClickListener: OnItemClickListener? = null
    var allowFullItemClickListener: Boolean = false

    protected abstract fun onCreateViewHolder(binding: ViewDataBinding): VH

    protected abstract fun getLayoutIdForViewType(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                getLayoutIdForViewType(viewType),
                parent,
                false
            )
        return onCreateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (allowFullItemClickListener)
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(
                    it,
                    getDataForPosition(position),
                    position
                )
            }
    }

    fun getDataForPosition(position: Int): T {
        return list[position]
    }

    fun getDataList(): MutableList<T> {
        return list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addList(list: List<T>) {
        val from = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(from, this.list.size)
    }

    fun setItemAt(position: Int, item: T) {
        this.list[position] = item
        notifyItemChanged(position)
    }

    fun removeItemAt(position: Int) {
        this.list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeAllItems() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setItemListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    abstract inner class ViewHolder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: T) {
//            binding.setVariable(BR.data, obj)
//            binding.executePendingBindings()
        }
    }

}