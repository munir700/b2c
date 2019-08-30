package co.yap.modules.dashboard.models

class Transaction(
                   var type: String,
                   var vendor: String,
                   var imageUrl:String,
                   var time: String,
                   var category:String,
                   var amount: String,
                   var currency:String){
}