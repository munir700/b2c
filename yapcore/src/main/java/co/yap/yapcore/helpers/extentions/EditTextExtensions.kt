package co.yap.yapcore.helpers.extentions

import android.text.InputFilter
import android.widget.EditText
import co.yap.yapcore.R
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

fun EditText.applyAmountFilters(units: Int = 10, decimal: Int = 2) {
    this.filters =
        arrayOf(
            InputFilter.LengthFilter(units),
            DecimalDigitsInputFilter(decimal)
        )

    // unitsCount and decimalCount are also using in xml try to use unit and decimal counts for unified stage
    //resources.getInteger(R.integer.unitsCount)),
    //resources.getInteger(R.integer.decimalCount))
}

