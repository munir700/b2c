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
                override fun onSingleTapUp(e: MotionEvent): Boolean {
//                    val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
////                    if (child != null && clickListener != null  ){
//////                        clickListener.onClick(child, recyclerView.getChildAdapterPosition(child))
////                        clickListener!!.onItemTouchEvent(child, recyclerView.getChildAdapterPosition(child!!))
////                    }
//                    if (child != null && clickListener != null) {
////            clickListener.onClick(child, recyclerView.getChildAdapterPosition(child))
//                        clickListener!!.scrollOnItemsTouchEvent(child, recyclerView.getChildAdapterPosition(child!!))
//                        return checkTouch
//                    }
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
                    if (child != null) {
                        clickListener.onLongClick(
                            child,
                            recyclerView.getChildAdapterPosition(child)
                        )
                    }
                }
            })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

        val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
        if (child != null) {
            clickListener.onItemTouchEvent(child, recyclerView.getChildAdapterPosition(child))
        }

        when (e.action) {
            MotionEvent.ACTION_UP -> {
                if (child != null) {
                    clickListener.scrollOnItemsTouchEvent(
                        child,
                        recyclerView.getChildAdapterPosition(child!!)
                    )
                    return checkTouch
                }
            }
        }

//        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//            clickListener.onClick(child, rv.getChildAdapterPosition(child))
//            Log.i("positionTouch","condition")
//
//        }
//        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//            clickListener.onClick(child, rv.getChildAdapterPosition(child))
//        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)

        fun onItemTouchEvent(view: View?, position: Int)

        fun scrollOnItemsTouchEvent(view: View?, position: Int)

    }
}