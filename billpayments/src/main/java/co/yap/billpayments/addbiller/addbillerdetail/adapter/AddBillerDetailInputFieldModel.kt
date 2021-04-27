package co.yap.billpayments.addbiller.addbillerdetail.adapter

import android.text.InputType
import androidx.databinding.ObservableField

data class AddBillerDetailInputFieldModel(
    var lable: String? = "",
    var description: String? = "",
    var minLength: Int? = 0,
    var maxLength: Int? = 0,
    var inputType: Int? = InputType.TYPE_CLASS_TEXT,
    var value: ObservableField<String>? = ObservableField()
)
