package co.yap.sendMoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.R
import co.yap.widgets.expandablelist.ExpandableRecycleViewAdapter
import co.yap.widgets.expandablelist.Parent
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
        val view = inflater.inflate(R.layout.bottom_sheet_pop, container, false)

        val recycleView = view.findViewById<RecyclerView>(R.id.rvPop)
        val list = ArrayList<Parent>(purposeCategories?.size ?: 0)
        purposeCategories?.forEach {
            list.add(Parent(it.key ?: "", it.value))
        }

        val adapter = ExpandableRecycleViewAdapter(list)
        recycleView.layoutManager = LinearLayoutManager(view.context)
        recycleView.adapter = adapter
        adapter.setExpanded(false)
//        view.tvChooseEmail.setOnClickListener { mListener.onClick(view.tvChooseEmail.id, T) }
//        view.tvChooseSMS.setOnClickListener { mListener.onClick(view.tvChooseSMS.id, T) }
//        view.tvChooseWhatsapp.setOnClickListener {
//            mListener.onClick(
//                view.tvChooseWhatsapp.id,
//                T
//            )
//        }
        return view
    }

    interface OnItemClickListener {
        fun onClick(viewId: Int, T: Any)
    }
}