package co.yap.yapcore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseBindingAdapter<VH : BaseBindingHolder>(val context: Context) : BaseAdapter() {

//    var onItemClickListener: OnItemClickListener? = null
//        get() {
//            if (field == null) this.onItemClickListener = OnItemClickListener.DEFAULT
//            return field
//        }

    override fun getItem(position: Int): Any {
        return getDataForPosition(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view
        var holder: VH? = null
        if (view == null) {
            val layoutInflater = LayoutInflater.from(context)
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, getLayoutForPos(position), parent, false)
            view = binding.root
            holder = createViewHolder(binding)
            view.tag = holder
        } else {
            holder = view.tag as VH
        }

        holder.adapterPosition = position
        holder.bind(getDataForPosition(position) as Object)

        return view
    }

    abstract fun createViewHolder(binding: ViewDataBinding): VH

    abstract fun getLayoutForPos(pos: Int): Int

    protected abstract fun getDataForPosition(position: Int): Any

    interface OnItemClickListener {
        fun onItemClick(view: View, pos: Int)

//        companion object {

//            var DEFAULT = { view, pos -> }
//        }

    }
}
