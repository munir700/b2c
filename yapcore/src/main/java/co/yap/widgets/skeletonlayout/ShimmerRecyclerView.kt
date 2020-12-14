package co.yap.widgets.skeletonlayout

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.R

class ShimmerRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    var shimmerLayoutId: Int = 0
        set(value) {
            field = value
            shimmerLayoutId?.let {

                invalidate()
            }
        }
 
    var itemCount: Int = 0
        set(value) {
            field = value
            itemCount?.let {

                invalidate()
            }
        }

    private var  skeleton: Skeleton

    init {
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ShimmerRecyclerview, 0, 0)
        shimmerLayoutId =
            attributes.getInteger(R.styleable.ShimmerRecyclerview_shimmerLayoutId, shimmerLayoutId)
        itemCount =
            attributes.getInteger(R.styleable.ShimmerRecyclerview_itemCount, itemCount)

        skeleton = applySkeleton(shimmerLayoutId, itemCount)
        adapter?.registerAdapterDataObserver(MyDataObserverAdapter())

        attributes.recycle()
    }

    inner class MyDataObserverAdapter: RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            if(adapter?.itemCount==0){
                skeleton.showSkeleton()
            }else{
                skeleton.showOriginal()
            }
        }
    }
}