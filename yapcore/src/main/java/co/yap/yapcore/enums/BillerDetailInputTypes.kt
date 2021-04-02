package co.yap.yapcore.enums

import android.text.InputType

enum class BillerDetailInputTypes(val inputType: Int) {
    ALPHA_NUMERIC(InputType.TYPE_CLASS_TEXT),
    NUMERIC(InputType.TYPE_CLASS_NUMBER)
}
