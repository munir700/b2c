package co.yap.yapcore.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEmptySupport : RecyclerView {
    private var emptyView: View? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context, attrs, defStyle
    )

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyLayout: View) {
        emptyView = emptyLayout
    }

    private val emptyObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            if (adapter?.itemCount == 0) {
                emptyView?.visibility = View.VISIBLE
                visibility = View.GONE
            } else {
                emptyView?.visibility = View.GONE
                visibility = View.VISIBLE
            }
        }
    }
}
