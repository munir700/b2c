package co.yap.sendmoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.databinding.BottomSheetPopBinding
import co.yap.widgets.expandablelist.PopParentAdapter
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PopListBottomSheet(
    private val mListener: OnItemClickListener,
    private val purposeCategories: Map<String?, List<PurposeOfPayment>>?
) : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = BottomSheetPopBinding.inflate(layoutInflater, container, false)
        val titleList = purposeCategories?.keys?.let { ArrayList(it) } ?: arrayListOf()
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter =
            PopParentAdapter(titleList, purposeCategories, binding.recyclerView, mListener)
        return binding.root
    }

}