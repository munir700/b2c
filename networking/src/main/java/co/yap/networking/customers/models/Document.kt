package co.yap.networking.customers.models

class Document {
    var contentType: String = ""
    var imageText: String = ""
    var pages: List<DocumentPage> = arrayListOf()
}