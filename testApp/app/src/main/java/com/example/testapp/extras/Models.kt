package com.example.testapp.extras

class Models {
    data class AnswerLogin(
        var acceso:String,
        var error:String,
        var token:String
    )

    data class Product(
        var id:Int,
        var code:String,
        var name:String,
        var price:Float,
        var stock:Float
    )
}