package co.yap.modules.location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import co.yap.widgets.CoreCircularImageView
import co.yap.yapcore.R

class POBCountryAdapter(
    val context: Context,
    private val objects: List<POBCountry>
) : BaseAdapter() {

    override fun getItem(position: Int): POBCountry {
        return objects[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return objects.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.item_place_of_birth_country, parent, false)
        val label = row.findViewById(R.id.textView) as TextView
        val flag = row.findViewById(R.id.flag_img) as CoreCircularImageView
        label.text = objects[position].name
        flag.setImageResource(objects[position].getFlagDrawableResId(label.context))

        if (position == 0) {//Special style for dropdown header
            label.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
        }
        return row
    }
}