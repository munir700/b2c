package co.yap.billpayments.billerdetail.adapter

import co.yap.yapcore.enums.BillerDetailInputTypes

data class BillerDetailModel(
    var background: Int,
    var drawableEnd: Int,
    var isOptional: Boolean,
    var hasDialog: Boolean,
    var dialogDescription: String,
    var dialogTitle: String,
    var placeHolder: String,
    var errorText: String,
    var maxLength: Int,
    var minLength: Int,
    var validLengths: Int,
    var billerDetailInputTypes: BillerDetailInputTypes,
    var key: String,
    var value:String
)
