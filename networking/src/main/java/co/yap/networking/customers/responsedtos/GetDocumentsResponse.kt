package co.yap.networking.customers.responsedtos

import co.yap.networking.customers.models.Document
import co.yap.networking.models.ApiResponse

class GetDocumentsResponse : ApiResponse() {
    var data: List<Document> = arrayListOf()
    var documentType: String = ""
}