package co.yap.yapcore


import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.helpers.extentions.onClick
import co.yap.yapcore.helpers.extentions.onLongClick
import co.yap.yapcore.interfaces.OnItemClickListener


abstract class BaseRVAdapter<T : ApiResponse, VM : BaseListItemViewModel<T>, VH : BaseViewHolder<T, VM>>
    (internal var datas: MutableList<T>, private var navigation: NavController?) :
    RecyclerView.Adapter<VH>() {

    @Nullable
    var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount() = datas.count()

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (datas.size > position)
            holder.setItem(datas[position], position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewModel = createViewModel(viewType)
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)
        val mDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        mDataBinding?.setVariable(getVariableId(), viewModel)
        val holder: VH = getViewHolder(view, viewModel, mDataBinding!!, viewType)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(
                it,
                datas[holder.adapterPosition],
                holder.adapterPosition
            )

        }
        return holder
    }

    override fun getItemId(position: Int) = position.toLong()
    override fun getItemViewType(position: Int) = position
    protected fun createViewModel(viewType: Int): VM {
        val viewModel: VM = getViewModel(viewType)
        viewModel.onCreate(Bundle(), navigation)
        navigation?.let { onItemClickListener = viewModel }
        return viewModel
    }

    @LayoutRes
    abstract fun getLayoutId(viewType: Int): Int

    abstract fun getViewHolder(
        view: View,
        viewModel: VM,
        mDataBinding: ViewDataBinding,
        viewType: Int
    ): VH

    abstract fun getViewModel(viewType: Int): VM
    abstract fun getVariableId(): Int

    fun addAll(datas: List<T>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }

    fun setData(@Nullable newData: MutableList<T>) {
        if (this.datas !== newData) {
            this.datas = newData
        }
        notifyDataSetChanged()
    }

    fun getData() = this.datas
    fun add(type: T) {
        this.datas.add(type)
        notifyItemInserted(this.datas.size - 1)
        notifyDataSetChanged()
    }

    fun removeAll() {
        this.datas.clear()
        notifyDataSetChanged()
    }

    fun remove(type: T) {
        val position = this.datas.indexOf(type)
        removeItemAt(position)
//        this.datas.remove(type)
//        notifyItemRemoved(position)
//        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int) {
        this.datas.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    fun removeAllItems() {
        this.datas.clear()
        notifyDataSetChanged()
    }

    fun change(newItem: T, oldItem: T) {
        val position = this.datas.indexOf(oldItem)
        this.datas.set(position, newItem)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

}