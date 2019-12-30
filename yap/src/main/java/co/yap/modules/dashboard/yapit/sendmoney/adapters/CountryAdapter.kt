package co.yap.modules.dashboard.yapit.sendmoney.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import co.yap.R
import co.yap.countryutils.country.Country
import co.yap.widgets.CoreCircularImageView


class CountryAdapter(context: Context, resource: Int, val objects: List<Country>) :
    ArrayAdapter<String>(context, resource, objects.map { it.getName() }) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent);
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent);
    }


    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.item_country, parent, false)
        val label = row.findViewById(R.id.textView) as TextView
        val flag = row.findViewById(R.id.flag_img) as CoreCircularImageView
        label.text = objects[position].getName()
        flag.setImageResource(objects[position].getFlagDrawableResId())

        if (position == 0) {//Special style for dropdown header
            label.setTextColor(context.resources.getColor(R.color.light_grey))
        }
        return row
    }
}