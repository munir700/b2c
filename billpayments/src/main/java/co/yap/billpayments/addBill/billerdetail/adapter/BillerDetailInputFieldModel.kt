package co.yap.billpayments.addBill.billerdetail.adapter

import android.text.InputType
import androidx.databinding.ObservableField

data class BillerDetailInputFieldModel(
    var lable: String? = "",
    var placeholder: String? = "",
    var minLength: Int? = 0,
    var maxLength: Int? = 0,
    var inputType: Int? = InputType.TYPE_CLASS_TEXT,
    var value: ObservableField<String>? = ObservableField()
)
