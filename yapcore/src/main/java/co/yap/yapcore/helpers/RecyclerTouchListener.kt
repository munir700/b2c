package co.yap.yapcore.helpers

import android.content.Context
import android.text.method.Touch.onTouchEvent
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class RecyclerTouchListener(
    context: Context,
    recyclerView: RecyclerView,
    private val clickListener: ClickListener?
) : RecyclerView.OnItemTouchListener {

    private val gestureDetector: GestureDetector

    init {
        gestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
//                    val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
//                    if (child != null && clickListener != null  ){
////                        clickListener.onClick(child, recyclerView.getChildAdapterPosition(child))
//                        clickListener!!.onItemTouchEvent(child, recyclerView.getChildAdapterPosition(child!!))
//                    }

                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(
                            child,
                            recyclerView.getChildAdapterPosition(child)
                        )
                    }
                }
            })
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
//        Log.i("positionTouch","onInterceptTouchEvent")

         val child = rv.findChildViewUnder(e.getX(), e.getY())
        if (child != null && clickListener != null  ){
//            clickListener.onClick(child, rv.getChildAdapterPosition(child))
            clickListener!!.onItemTouchEvent(child, rv.getChildAdapterPosition(child!!))
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
        Log.i("positionTouch","onTouchEvent")

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    interface ClickListener {
        fun onClick(view: View, position: Int)

        fun onLongClick(view: View?, position: Int)

        fun onItemTouchEvent(view: View?, position: Int)

    }
}