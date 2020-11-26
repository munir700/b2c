package co.yap.yapcore.helpers.extentions

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.interfaces.OnItemClickListener
import co.yap.yapcore.managers.SessionManager
import java.util.*

var oldPosition = -1
val countries: ArrayList<Country> = SessionManager.getCountries()

fun FragmentActivity.launchBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    label: String = "Change home country",
    viewType: Int = Constants.VIEW_WITH_FLAG,
    selectedCountry: () -> Unit
) {

    this.supportFragmentManager?.let {
        val coreBottomSheet = itemClickListener?.let { itemListener ->
            CoreBottomSheet(
                itemListener,
                bottomSheetItems = getCountries(countries, this).toMutableList(),
                headingLabel = label,
                viewType = viewType
            )
        }
        selectedCountry.invoke()
        coreBottomSheet?.show(it, "")
    }
}

fun Fragment.launchBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    label: String = "Change home country",
    viewType: Int = Constants.VIEW_WITH_FLAG,
    selectedCountry: () -> Unit
) {
    this.fragmentManager?.let {
        val coreBottomSheet = itemClickListener?.let { itemListener ->
            CoreBottomSheet(
                itemListener,
                bottomSheetItems = getCountries(countries, requireContext()).toMutableList(),
                headingLabel = label,
                viewType = viewType
            )
        }
        coreBottomSheet?.show(it, "")
        selectedCountry.invoke()
    }
}

private fun getCountries(
    countries: ArrayList<Country>,
    context: Context
): ArrayList<Country> {
    countries.filter { it.isoCountryCode2Digit != "AE" }.forEach {
        it.subTitle = it.getName()
        it.sheetImage = CurrencyUtils.getFlagDrawable(
            context,
            it.isoCountryCode2Digit.toString()
        )
    }
    return countries
}

fun getSelectedCountry(position : String?){
    val position =
        countries.indexOf(countries.find { it.isoCountryCode2Digit == position})
    if (oldPosition == -1) {
        oldPosition = position
        countries[oldPosition].isSelected = true
    } else {
        countries[oldPosition].isSelected = false
        oldPosition = position
        countries[oldPosition].isSelected = true
    }
}
