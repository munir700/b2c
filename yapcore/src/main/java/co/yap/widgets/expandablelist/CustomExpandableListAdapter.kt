package co.yap.widgets.expandablelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import co.yap.networking.transactions.responsedtos.purposepayment.PurposeOfPayment
import co.yap.yapcore.R


class CustomExpandableListAdapter constructor(
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: Map<String?, List<PurposeOfPayment>>?
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): PurposeOfPayment? {
        return this.dataList?.get(this.titleList[listPosition])?.get(expandedListPosition)
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.child_row, null)
        }
        val expandedListTextView = convertView?.findViewById<TextView>(R.id.tvC)
        expandedListTextView?.text = expandedListText?.purposeDescription
        return convertView!!
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList?.get(this.titleList[listPosition])?.size ?: 0
    }

    override fun getGroup(listPosition: Int): String {
        return this.titleList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition)
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.parent_row, null)
        }
        val listTitleTextView = convertView?.findViewById<TextView>(R.id.tvP)
        val arrow = convertView?.findViewById<ImageView>(R.id.arrow)
        listTitleTextView?.text = listTitle
        arrow?.setImageResource(R.drawable.ic_down)
        return convertView!!
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

    fun getGroupView(listView: ExpandableListView, groupPosition: Int) {
        val packedPosition: Long =
            ExpandableListView.getPackedPositionForGroup(groupPosition)
        val flatPosition: Int = listView.getFlatListPosition(packedPosition)
        val first: Int = listView.firstVisiblePosition
        val v = listView.getChildAt(flatPosition - first)
        val arrow = v?.findViewById<ImageView>(R.id.arrow)
        arrow?.setImageResource(R.drawable.ic_down)
    }
}