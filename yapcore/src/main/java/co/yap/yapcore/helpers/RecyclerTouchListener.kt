package co.yap.yapcore.helpers

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class RecyclerTouchListener(
    context: Context, val checkTouch: Boolean,
    var recyclerView: RecyclerView,
    private val clickListener: ClickListener
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    if (e == null) return false
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null) {
                        clickListener.onClick(child, recyclerView.getChildAdapterPosition(child))
                    }
                    return true
                }
            })

        recyclerView.addOnItemTouchListener(this)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e)
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        //fun onLongClick(view: View?, position: Int)

        //fun onItemTouchEvent(view: View?, position: Int)

        //fun scrollOnItemsTouchEvent(view: View?, position: Int)

    }
}