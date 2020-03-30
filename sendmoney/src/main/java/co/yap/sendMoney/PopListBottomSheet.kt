package co.yap.sendMoney

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.sendmoney.R
import co.yap.widgets.expandablelist.CustomExpandableListAdapter
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

        val expandableListView = view.findViewById<ExpandableListView>(R.id.expandableListView)

        val titleList = ArrayList(purposeCategories?.keys)
        val adapter = CustomExpandableListAdapter(
            view.context,
            titleList as ArrayList<String>,
            purposeCategories
        )
        val width = view.resources.displayMetrics.widthPixels
        expandableListView.setIndicatorBounds(
            width - getPixelFromDips(50f),
            width - getPixelFromDips(10f)
        );
        expandableListView?.setAdapter(adapter)

        expandableListView?.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            mListener.onClick(
                v.id,
                purposeCategories?.get((titleList)[groupPosition])?.get(childPosition)
            )
            false
        }
        var previousGroup = -1
        expandableListView?.setOnGroupExpandListener {
            if (it != previousGroup) expandableListView.collapseGroup(previousGroup)
            previousGroup = it

        }
        return view
    }

    private fun getPixelFromDips(pixels: Float): Int {
        val scale = resources.displayMetrics.density;
        return (pixels * scale + 0.5f).toInt()
    }

    interface OnItemClickListener {
        fun onClick(viewId: Int, T: PurposeOfPayment?)
    }
}