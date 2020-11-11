package co.yap.widgets.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.extentions.afterTextChanged
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CoreBottomSheet(
    private val mListener: OnItemClickListener,
    private val bottomSheetItems: MutableList<CoreBottomSheetData>,
    private val headingLabel: String? = null,
    private val viewType: Int = Constants.VIEW_WITHOUT_FLAG
) : BottomSheetDialogFragment(), ICoreBottomSheet.View {

    val adapter : CoreBottomSheetAdapter by lazy {
        CoreBottomSheetAdapter(bottomSheetItems, viewType)
    }

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_bottom_sheet, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvBottomSheet)
        val tvHeading: TextView = view.findViewById(R.id.tvlabel)
        val svListing: AppCompatEditText = view.findViewById(R.id.etSearch)
         if (viewType == Constants.VIEW_WITHOUT_FLAG) viewModel.state.searchBarVisibility.value = false
        headingLabel?.let {
            tvHeading.text = it
        }
        svListing.afterTextChanged {
            adapter.filter.filter(it)
            adapter.notifyDataSetChanged()
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.allowFullItemClickListener = true
        adapter.onItemClickListener = myListener
        recyclerView.adapter = adapter
        return view
    }

    private val myListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            mListener.onItemClick(view, this@CoreBottomSheet, pos)
     /*       mListener.onItemClick(view, data, pos)
            dismiss()*/
        }
    }
    override val viewModel: CoreBottomSheetViewModel
        get() = ViewModelProviders.of(this).get(CoreBottomSheetViewModel::class.java)

    override fun showLoader(isVisible: Boolean) {
    }

    override fun showToast(msg: String) {
    }

    override fun showInternetSnack(isVisible: Boolean) {
    }

    override fun isPermissionGranted(permission: String): Boolean {
        return true
    }

    override fun requestPermissions() {
    }

    override fun getString(resourceKey: String): String {
        return ""
    }

    override fun onNetworkStateChanged(isConnected: Boolean) {
    }


}