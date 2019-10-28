package co.yap.modules.dashboard.yapit.y2y.home.adaptors

import android.content.Context
import android.content.pm.ResolveInfo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import co.yap.R


class ShareIntentListAdaptor(
    context: Context,
    private val layoutId: Int,
    private val items: List<Any>
) :
    ArrayAdapter<Any>(context, layoutId, items) {


    override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val row = layoutInflater.inflate(layoutId, null)
        val label = row.findViewById(R.id.tvAppName) as TextView
        label.text =
            (items[pos] as ResolveInfo).activityInfo.applicationInfo.loadLabel(context.packageManager)
                .toString()
        val image = row.findViewById(R.id.ivAppIcon) as ImageView
        image.setImageDrawable(
            (items[pos] as ResolveInfo).activityInfo.applicationInfo.loadIcon(
                context.packageManager
            )
        )

        return row
    }
}
