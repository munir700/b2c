package com.yap.uikit.widgets.bottomsheet

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class BottomSheetBuilder {
    var adapter: Adapter<RecyclerView.ViewHolder>? = null
    var theme: Int? = null
    var isShowTopBarView: Boolean = true

    fun build(): BottomSheetUIDialog? {
        return BottomSheetUIDialog().newInstance(this)
    }

    fun show(fragmentManager: FragmentManager, tag: String?) {
        val dialog: BottomSheetUIDialog? = build()
        dialog?.show(fragmentManager, tag)
    }

    fun setAdapter(adapter: Adapter<RecyclerView.ViewHolder>): BottomSheetBuilder {
        this.adapter = adapter
        return this
    }

    fun setTheme(theme: Int?): BottomSheetBuilder {
        this.theme = theme
        return this
    }

    fun showTopBar(isShow: Boolean): BottomSheetBuilder {
        this.isShowTopBarView = isShow
        return this
    }

}