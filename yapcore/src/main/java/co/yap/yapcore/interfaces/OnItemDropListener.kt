package co.yap.yapcore.interfaces

import android.view.View

interface OnItemDropListener {
    fun onItemDrop(view: View, pos: Int, data: Any)
}