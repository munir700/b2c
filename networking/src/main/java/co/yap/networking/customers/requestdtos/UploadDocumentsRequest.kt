package co.yap.networking.customers.requestdtos

import java.util.*

data class UploadDocumentsRequest(
    val filePaths: List<String>,
    val documentType: String,
    val firstName: String,
    val lastName: String,
    val nationality: String,
    val dateExpiry: Date,
    val dob: Date,
    val fullName: String,
    val gender: String, // M/F
    val identityNo: String
)