package com.yap.uikit.widgets.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yap.uikit.R
import com.yap.uikit.databinding.LayoutBottomSheetDialogBinding


class BottomSheetUIDialog : BottomSheetDialogFragment() {
    private var fragment: BottomSheetUIDialog? = null
    private var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private var theme: Int? = null
    private var isShowTopBarView: Boolean = true
    lateinit var binding: LayoutBottomSheetDialogBinding
    fun newInstance(builder: BottomSheetBuilder): BottomSheetUIDialog? {
        if (fragment == null) {
            fragment = BottomSheetUIDialog()
        }
        val arguments = Bundle()
        fragment?.adapter = builder.adapter
        fragment?.theme = builder.theme
        fragment?.isShowTopBarView = builder.isShowTopBarView
        fragment?.arguments = arguments
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.layout_bottom_sheet_dialog,
                container,
                false
            )
        return binding.root
    }

    override fun getTheme(): Int {
        return theme ?: super.getTheme()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()

        val layoutManager: RecyclerView.LayoutManager
        layoutManager = LinearLayoutManager(context)

        if (!isShowTopBarView)
            binding.vTopBar.visibility = View.GONE
        binding.rvBottomSheet.adapter = adapter
        binding.rvBottomSheet.layoutManager = layoutManager
    }


}