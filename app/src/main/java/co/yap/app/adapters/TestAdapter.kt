package co.yap.app.adapters

import android.content.Context
import androidx.databinding.ViewDataBinding
import co.yap.yapcore.BaseBindingAdapter

class TestAdapter(context: Context) : BaseBindingAdapter<TestViewHolder>(context) {
    override fun createViewHolder(binding: ViewDataBinding): TestViewHolder {
        return TestViewHolder(binding)
    }

    override fun getLayoutForPos(pos: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getDataForPosition(position: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}