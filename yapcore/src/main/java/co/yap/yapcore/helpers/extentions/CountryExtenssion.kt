package co.yap.yapcore.helpers.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import co.yap.countryutils.country.Country
import co.yap.countryutils.country.utils.CurrencyUtils
import co.yap.networking.coreitems.CoreBottomSheetData
import co.yap.widgets.bottomsheet.BottomSheetConfiguration
import co.yap.widgets.bottomsheet.CoreBottomSheet
import co.yap.widgets.bottomsheet.multi_selection_bottom_sheet.CoreMultiSelectionBottomSheet
import co.yap.yapcore.R
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.getCountryCodeForRegion
import co.yap.yapcore.interfaces.OnItemClickListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

fun FragmentActivity.launchBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    label: String = "Change home country",
    viewType: Int = Constants.VIEW_WITH_FLAG,
    countriesList: List<Country>? = null
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
                    viewType = viewType,
                    configuration = BottomSheetConfiguration(
                        heading = label,
                        showSearch = true,
                        showHeaderSeparator = true
                    )
                )
            coreBottomSheet.show(it, "")
        }
    }
}

fun Fragment.launchBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    label: String = "Change home country",
    viewType: Int = Constants.VIEW_WITH_FLAG,
    countriesList: List<Country>? = null
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

fun FragmentActivity.launchMultiSelectionBottomSheet(
    itemClickListener: OnItemClickListener? = null,
    configuration: BottomSheetConfiguration,
    viewType: Int = Constants.VIEW_WITH_FLAG,
    countriesList: List<Country>? = null
) {
    this.supportFragmentManager.let {
        countriesList?.let { countriesList ->
            val coreBottomSheet =
                CoreMultiSelectionBottomSheet(
                    itemClickListener,
                    bottomSheetItems = parseCountries(
                        this,
                        countriesList as ArrayList<Country>
                    ).toMutableList(),
                    configuration = configuration,
                    viewType = viewType
                )
            coreBottomSheet.show(it, "")
        }
    }
}

fun FragmentActivity.launchBottomSheetForMutlipleCountries(
    countriesList: ArrayList<co.yap.networking.customers.responsedtos.sendmoney.Country>,
    itemClickListener: OnItemClickListener? = null
) {
    this.supportFragmentManager.let {
        val coreBottomSheet =
            CoreBottomSheet(
                itemClickListener,
                bottomSheetItems = getBottomSheetDataList(
                    countriesList

                ),
                viewType = Constants.VIEW_ITEM_WITH_FLAG_AND_CODE,
                configuration = BottomSheetConfiguration(
                    heading = "Select Country",
                    showSearch = false,
                    showHeaderSeparator = true
                )
            )
        coreBottomSheet.show(it, "")

    }
}

fun Context.getBottomSheetDataList(countries: ArrayList<co.yap.networking.customers.responsedtos.sendmoney.Country>): MutableList<CoreBottomSheetData> {
    val list: ArrayList<CoreBottomSheetData> = arrayListOf()
    countries.forEach {
        list.add(
            CoreBottomSheetData(
                subTitle = it.name,
                sheetImage = CurrencyUtils.getFlagDrawable(
                    this,
                    it.isoCountryCode2Digit.toString()
                ),
                content = getCountryCodeForRegion(it.isoCountryCode2Digit ?: "PK"),
                key = it.isoCountryCode2Digit
            )
        )
//        it.subTitle = it.getName()
//        it.sheetImage = CurrencyUtils.getFlagDrawable(
//            this,
//            it.isoCountryCode2Digit.toString()
//        )
//        it.content = getCountryCodeForRegion(it.isoCountryCode2Digit ?: "PK")
//        it.key = it.isoCountryCode2Digit
    }
    return list.toMutableList()
}

fun Context.getDropDownIconByName(countryName: String): Int {
    if (!TextUtils.isEmpty(countryName)) {
        var name = countryName
        if (countryName.length == 2 || countryName.length == 3) {
            // it is probably a country iso isoCountryCode2Digit
            name = "draw_icon_" + countryName.toLowerCase()
        }
        return resources.getIdentifier(
            name,
            "drawable",
            packageName
        )
    }
    return 0
}

//fun Context.fromSuperAppCountries(): ArrayList<Country> {
//    val gson = Gson()
//    val parser = JsonParser()
//    val byteArray =
//        Base64.decode(resources.getString(R.string.country_encoded_base64), Base64.DEFAULT)
//    val data = parser.parse(String(byteArray)).asJsonArray
//    val jsonString = gson.toJson(data)
//    val sType = object : TypeToken<ArrayList<Country>>() {}.type
//    return gson.fromJson(jsonString, sType)
//}

@SuppressLint("ClickableViewAccessibility")
fun TextInputEditText.setTouchListener(
    callback: () -> Unit
) {
    setOnTouchListener(View.OnTouchListener { v, event ->
        val drawableLeft = 0
        if (event.action == MotionEvent.ACTION_UP && event.rawX <=
            this.compoundDrawables[drawableLeft].bounds.width()
        ) {
            callback.invoke()
            return@OnTouchListener true
        }
        false
    })
}

fun TextInputLayout.requestFocusForField() {
    this.requestFocus()
    this.boxStrokeColor = resources.getColor(R.color.colorPrimary)
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
        InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY
    )
}

//fun TextInputLayout.requestDefaultFocus() {
//    this.requestFocus()
//    this.boxStrokeColor = resources.getColor(R.color.textInputBackgroundColor)
//    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
//        InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY
//    )
//}