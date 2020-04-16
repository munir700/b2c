package co.yap.yapcore.helpers.extentions

import android.os.Build
import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.interfaces.OnItemDropListener

fun onDrag(view: View, pos: Int, event: DragEvent, data: Any, listener: OnItemDropListener?, mScrollView: RecyclerView): Boolean {
    when (event.action) {
        DragEvent.ACTION_DRAG_STARTED -> return true
        DragEvent.ACTION_DRAG_ENTERED -> {
            listener?.onItemEntered(view)
            return true
        }
        DragEvent.ACTION_DRAG_LOCATION -> {
            val y = Math.round(event.getY()).toInt()

            val translatedY: Int = y - mScrollView.computeVerticalScrollOffset()

            val threshold = 50
            // make a scrolling up due the y has passed the threshold
            // make a scrolling up due the y has passed the threshold
            if (y < threshold) { // make a scroll up by 30 px
                mScrollView.smoothScrollBy(0, - y)
            } else  // make a autoscrolling down due y has passed the 500 px border
//                if (y + threshold > 500) { // make a scroll down by 30 px
                    mScrollView.smoothScrollBy(0, y)
//                }

            return true
        }
        DragEvent.ACTION_DRAG_EXITED -> {
            listener?.onItemExited(view)
            return true
        }
        DragEvent.ACTION_DROP -> {
            listener?.onItemDrop(view, pos, data)
            return true
        }
        DragEvent.ACTION_DRAG_ENDED -> return true
        else -> {
        }
    }
    return false
}

fun startDrag(view: View): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        view.startDragAndDrop(null, View.DragShadowBuilder(view), null, 0);
    } else {
        @Suppress("DEPRECATION")
        view.startDrag(null, View.DragShadowBuilder(view), null, 0);
    }
    return true
}