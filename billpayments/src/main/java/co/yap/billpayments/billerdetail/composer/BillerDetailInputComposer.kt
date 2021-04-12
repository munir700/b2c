package co.yap.billpayments.billerdetail.composer

import android.text.InputType
import co.yap.billpayments.billerdetail.adapter.BillerDetailInputFieldModel
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.yapcore.enums.BillerDetailInputType


interface BillerDetailsInputComposer {
    fun compose(ioCatLogs: ArrayList<IoCatalogModel>): MutableList<BillerDetailInputFieldModel>
}

class BillerDetailInputComposer : BillerDetailsInputComposer {
    override fun compose(ioCatLogs: ArrayList<IoCatalogModel>): MutableList<BillerDetailInputFieldModel> {
        val list: MutableList<BillerDetailInputFieldModel> = mutableListOf()
        list.add(
            BillerDetailInputFieldModel(
                lable = "Enter account nickname",
                placeholder = "nickname",
                maxLength = 255,
                minLength = 1,
                inputType = InputType.TYPE_CLASS_TEXT
            )
        )
        ioCatLogs.map { ioCatLog ->
            list.add(
                BillerDetailInputFieldModel(
                    lable = ioCatLog.name,
                    placeholder = ioCatLog.description,
                    maxLength = ioCatLog.maxLength,
                    minLength = ioCatLog.minLength,
                    inputType = getInputType(ioCatLog.dataType)
                )
            )
        }
        return list
    }

    private fun getInputType(dataType: String?): Int {
        return when (dataType) {
            BillerDetailInputType.Alphanumeric.name -> {
                BillerDetailInputType.Alphanumeric.inputType
            }
            BillerDetailInputType.Numeric.name -> {
                BillerDetailInputType.Numeric.inputType
            }
            else -> {
                BillerDetailInputType.Alphanumeric.inputType
//                throw IllegalStateException("Not a valid data type $dataType")
            }
        }
    }
}
