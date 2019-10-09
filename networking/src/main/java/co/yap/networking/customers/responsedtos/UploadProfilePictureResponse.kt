package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse

data class UploadProfilePictureResponse(
    var data: Data,
    var errors: Any?
) : ApiResponse() {
    data class Data(
        var imageURL: String
    )
}