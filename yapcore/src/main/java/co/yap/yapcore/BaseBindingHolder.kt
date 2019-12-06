package co.yap.yapcore

import androidx.databinding.ViewDataBinding

abstract class BaseBindingHolder(val binding: ViewDataBinding) {

    var adapterPosition: Int = 0
//    private lateinit var viewDataBinding: ViewDataBinding

    var itemView = binding.root

    fun bind(obj: Object) {

//        binding.setVariable(getBindingVariable(), obj)
//        binding.executePendingBindings()
//        viewDataBinding = binding

        binding.setVariable(getBindingVariable(), obj)
        binding.executePendingBindings()

//        binding.dataList = binding.dataList
//        binding.executePendingBindings()

    }
    abstract fun getBindingVariable(): Int
}
