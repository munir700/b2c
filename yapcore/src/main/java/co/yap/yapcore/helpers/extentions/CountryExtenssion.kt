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

fun FragmentActivity.launchBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    label: String = "Change home country",
    viewType: Int = Constants.VIEW_WITH_FLAG,
    countriesList: List<Country>? = SessionManager.getCountries()
) {
    this.supportFragmentManager.let {
        countriesList?.let { countriesList ->
            val coreBottomSheet =
                CoreBottomSheet(
                    itemClickListener,
                    bottomSheetItems = parseCountries(
                        this,
                        countriesList as ArrayList<Country>
                    ).toMutableList(),
                    headingLabel = label,
                    viewType = viewType
                )
            coreBottomSheet.show(it, "")
        }
    }
}

fun Fragment.launchBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    label: String = "Change home country",
    viewType: Int = Constants.VIEW_WITH_FLAG,
    countriesList: List<Country>? = SessionManager.getCountries()
) {
    this.requireActivity().launchBottomSheet(itemClickListener, label, viewType, countriesList)
}

private fun parseCountries(context: Context, countries: ArrayList<Country>): ArrayList<Country> {
    countries.forEach {
        it.subTitle = it.getName()
        it.sheetImage = CurrencyUtils.getFlagDrawable(
            context,
            it.isoCountryCode2Digit.toString()
        )
    }
    return countries
}



