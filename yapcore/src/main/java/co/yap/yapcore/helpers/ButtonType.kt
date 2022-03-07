package co.yap.yapcore.helpers

import androidx.annotation.Keep

@Keep
enum class ButtonType(val type: String) {
    NEXT("Next"),
    CLOSE("Close"),
    CONTINUE("Continue"),
    BACK("Back")
}