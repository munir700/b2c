package co.yap.modules.subaccounts.account.card

import android.graphics.Color
import android.os.Build
import android.view.DragEvent
import android.view.View

class DragAndDropManager {
    var listener: OnItemDragDropListener? = null

    constructor(listener: OnItemDragDropListener?) {
        this.listener = listener
    }

    fun onItemLongClick(view: View): Boolean? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(null, View.DragShadowBuilder(view), null, 0);
        } else {
            @Suppress("DEPRECATION")
            view.startDrag(null, View.DragShadowBuilder(view), null, 0);
        }
        return true
    }

    fun onItemDrag(view: View, pos: Int, event: DragEvent, data: Any): Boolean? {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> return true
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.setBackgroundColor(Color.LTGRAY)
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> return true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.setBackgroundColor(Color.TRANSPARENT)
                return true
            }
            DragEvent.ACTION_DROP -> {
                view.setBackgroundColor(Color.TRANSPARENT)
                listener?.onItemDrop(view, pos, data)
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> return true
            else -> {
            }
        }
        return false
    }
}

interface OnItemDragDropListener {
    fun onItemDrop(view: View, pos: Int, data: Any)
}