package co.yap.yapcore.helpers.extentions

import android.os.Build
import android.view.DragEvent
import android.view.View
import co.yap.yapcore.interfaces.OnItemDropListener

fun onDrag(view: View, pos: Int, event: DragEvent, data: Any, listener: OnItemDropListener?): Boolean {
    when (event.action) {
        DragEvent.ACTION_DRAG_STARTED -> return true
        DragEvent.ACTION_DRAG_ENTERED -> {
            return true
        }
        DragEvent.ACTION_DRAG_LOCATION -> return true
        DragEvent.ACTION_DRAG_EXITED -> {
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