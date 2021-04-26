package co.yap.billpayments.addbiller.addbillerdetail.composer

import android.text.InputType
import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailInputFieldModel
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel
import co.yap.yapcore.enums.BillerDetailInputType


interface AddBillerDetailsInputComposer {
    fun compose(ioCatLogs: ArrayList<IoCatalogModel>): MutableList<AddBillerDetailInputFieldModel>
}

class AddBillerDetailInputComposer : AddBillerDetailsInputComposer {
    override fun compose(ioCatLogs: ArrayList<IoCatalogModel>): MutableList<AddBillerDetailInputFieldModel> {
        val list: MutableList<AddBillerDetailInputFieldModel> = mutableListOf()
        list.add(
            AddBillerDetailInputFieldModel(
                lable = "Enter nickname",
                description = "Enter nickname",
                maxLength = 255,
                minLength = 1,
                inputType = InputType.TYPE_CLASS_TEXT
            )
        )
        ioCatLogs.map { ioCatLog ->
            list.add(
                AddBillerDetailInputFieldModel(
                    lable = ioCatLog.name,
                    description = ioCatLog.description,
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
