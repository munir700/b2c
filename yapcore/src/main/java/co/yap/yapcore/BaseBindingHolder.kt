package co.yap.yapcore

import android.view.View
import androidx.databinding.ViewDataBinding


abstract class BaseBindingHolder(val binding: ViewDataBinding) {
    var adapterPosition: Int = 0

    lateinit var itemView: View
//        get() = binding.root

    fun bind(obj: Object) {
//        binding.setVariable(BR.get, obj)
        binding.executePendingBindings()
    }

}
