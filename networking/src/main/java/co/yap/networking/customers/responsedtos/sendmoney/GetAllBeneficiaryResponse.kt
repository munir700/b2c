package co.yap.networking.customers.responsedtos.sendmoney

import co.yap.networking.models.ApiResponse

data class GetAllBeneficiaryResponse(
//    var `data`: List<Data>,
    var data: List<Beneficiary>,
    var errors: Any?
) : ApiResponse()
/*{
    data class Data(
        var accountNo: String,
        var accountUuid: String,
        var beneficiaryId: String,
        var beneficiaryType: String,
        var country: String,
        var currency: String,
        var firstName: String,
        var id: Int,
        var lastName: String,
        var mobileNo: String,
        var title: String
    )
    }*/
