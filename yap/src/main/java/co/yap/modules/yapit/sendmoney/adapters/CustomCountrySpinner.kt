package co.yap.modules.yapit.sendmoney.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import co.yap.R
import co.yap.countryutils.country.Country
import kotlinx.android.synthetic.main.item_country.view.*
import kotlinx.android.synthetic.main.item_new_country.view.*


class CustomCountrySpinner(
    context: Context,
    val customCountryLAyout: Int,
    val values: ArrayList<Country>
) : ArrayAdapter<Country>(context, customCountryLAyout, values) {

    //       val count: Int= values.size
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(getContext())
    }

    override fun getItem(position: Int): Country {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
//    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
//
//        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
//        val label = super.getView(position, convertView, parent) as TextView
//        label.setTextColor(Color.BLACK)
//        // Then you can get the current item using the values array (Users array) and the current position
//        // You can NOW reference each method you has created in your bean object (Country class)
//        label.setText(values[position].getName())
//
//        // And finally return your dynamic (or custom) view for each spinner item
//        return label
//    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

//        val view = inflater.inflate(R.layout.item_country,p2)
//        val view = layoutInflater.inflate(R.layout.item_country, p2)
//        val view = layoutInflater.inflate(R.layout.item_country, p2)
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_country, p2, false
        )
//        view.tvCountryName.setText(values[p0].getName())
//        view.flag_img.setImageResource(values[p0].getFlagDrawableResId())

 view.tvCountryName.setText("Pk")
//        view.flag_img.setImageResource(values[p0].getFlagDrawableResId())


//        view.tvCountryName.setText(getItem(p0).getName())
//        view.flag_img.setImageResource(getItem(p0).getFlagDrawableResId())
        return view
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    override fun getDropDownView(
        position: Int, convertView: View,
        parent: ViewGroup
    ): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.setText(values[position].getName())

        return label
    }
}