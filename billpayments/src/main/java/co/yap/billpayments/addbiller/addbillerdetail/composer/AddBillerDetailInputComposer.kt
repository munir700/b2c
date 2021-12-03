package co.yap.billpayments.addbiller.addbillerdetail.composer

import co.yap.billpayments.addbiller.addbillerdetail.adapter.AddBillerDetailInputFieldModel
import co.yap.billpayments.utils.enums.BillerDetailInputType
import co.yap.networking.customers.responsedtos.billpayment.IoCatalogModel


interface AddBillerDetailsInputComposer {
    fun compose(ioCatLogs: ArrayList<IoCatalogModel>): MutableList<AddBillerDetailInputFieldModel>
}

class AddBillerDetailInputComposer : AddBillerDetailsInputComposer {
    override fun compose(ioCatLogs: ArrayList<IoCatalogModel>): MutableList<AddBillerDetailInputFieldModel> {
        val list: MutableList<AddBillerDetailInputFieldModel> = mutableListOf()
        ioCatLogs.map { ioCatLog ->
            list.add(
                AddBillerDetailInputFieldModel(
                    lable = ioCatLog.name,
                    description = ioCatLog.description,
                    maxLength = ioCatLog.maxLength,
                    minLength = ioCatLog.minLength,
                    inputType = getInputType(ioCatLog.dataType),
                    ioId = ioCatLog.ioId.toString()
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
