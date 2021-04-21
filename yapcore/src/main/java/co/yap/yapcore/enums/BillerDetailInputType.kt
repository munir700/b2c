package co.yap.yapcore.enums

import android.text.InputType

enum class BillerDetailInputType(val inputType: Int) {
    Alphanumeric(InputType.TYPE_CLASS_TEXT),
    Numeric(InputType.TYPE_CLASS_NUMBER)
}
