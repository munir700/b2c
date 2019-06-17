package co.yap.yapcore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseBindingRecyclerAdapter<T : Any, VH : RecyclerView.ViewHolder>(private val list: MutableList<T>) :
    RecyclerView.Adapter<VH>() {

    var onItemClickListener: OnItemClickListener? = null
//        get() {
//            if (field == null) this.onItemClickListener = OnItemClickListener.DEFAULT
//            return field
//        }

    protected abstract fun onCreateViewHolder(binding: ViewDataBinding): VH

    protected abstract fun getLayoutIdForViewType(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, getLayoutIdForViewType(viewType), parent, false)
        return onCreateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val obj = getDataForPosition(position)
//        holder.bind(obj)
//        holder.itemView.setOnClickListener({ view ->
//            onItemClickListener.onItemClick(
//                view,
//                holder.getAdapterPosition()
//            )
//        })
    }

    protected fun getDataForPosition(position: Int): T {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    protected fun setList(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, pos: Int)

//        companion object {
//
//            val DEFAULT = { view, pos -> }
//        }
    }

    abstract inner class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: T) {
//            binding.setVariable(BR.data, obj)
//            binding.executePendingBindings()
        }
    }

}
