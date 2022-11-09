package com.example.testapp.extras

class GlobalVariables {
    companion object{
        var url_app = "http://192.168.1.65:8000/"
        var url_login = url_app + "api/login"
        var url_get_products = url_app + "api/products/list"
        var url_save_products = url_app + "api/products/save"
        var url_delete_products = url_app + "api/products/delete"

        var TOKEN:String? = null
    }
}