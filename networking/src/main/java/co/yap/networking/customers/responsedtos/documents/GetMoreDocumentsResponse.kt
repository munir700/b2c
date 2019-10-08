package co.yap.networking.customers.responsedtos.documents

import co.yap.networking.models.ApiResponse

data class GetMoreDocumentsResponse (
    var data: Data,
    var errors: Any?
) : ApiResponse() {
    data class Data(
        var active: Boolean,
        var customerDocuments: List<CustomerDocument>,
        var customerUUID: String,
        var dateExpiry: String,
        var dateIssue: String,
        var dob: String,
        var documentType: String,
        var firstName: String,
        var fullName: String,
        var gender: String,
        var lastName: String,
        var legalStatus: Any?,
        var licenseNumber: Any?,
        var nationality: String,
        var passportNumber: Any?,
        var registrationAuthority: Any?,
        var registrationNumber: Any?,
        var tradeName: Any?
    ) {
        data class CustomerDocument(
            var active: Boolean,
            var contentType: String,
            var createdBy: String,
            var creationDate: String,
            var customerId: String,
            var customerUUID: String,
            var documentInformation: DocumentInformation,
            var documentType: String,
            var expired: Boolean,
            var fileName: String,
            var pageNo: Int,
            var updatedBy: String,
            var updatedDate: String,
            var uploadDate: String
        ) {
            data class DocumentInformation(
                var active: Boolean,
                var createdBy: String,
                var creationDate: String,
                var customerUUID: String,
                var dateExpiry: String,
                var dateIssue: String,
                var dob: String,
                var documentType: String,
                var firstName: String,
                var fullName: String,
                var gender: String,
                var identityNo: String,
                var lastName: String,
                var nationality: String,
                var updatedBy: String,
                var updatedDate: String
            )
        }
    }
}