package com.example.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.extras.GlobalVariables
import com.example.testapp.extras.Models
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(){
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Picasso
            .get()
            .load("https://carta.v-card.es/assets/images/user/login.png")
            .fit()
            .into(binding.imgLogin)

        binding.btnLogin.setOnClickListener{
            //Toast.makeText(this@MainActivity,"Hi "+binding.txtUser.editText?.text.toString(), Toast.LENGTH_SHORT).show()
            validationLogin()
        }
    }


    private fun validationLogin(){
        var url = GlobalVariables.url_login

        val formBody: RequestBody = FormBody.Builder()
            .add("email", binding.txtUser.editText?.text.toString())
            .add("password", binding.txtPass.editText?.text.toString())
            .build()

        val request = Request.Builder().url(url).post(formBody).build()
        val client = OkHttpClient()
        var objGson = Gson()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed server" + e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                //println("Successful " + response.body?.string())

                var respuesta = response.body?.string()
                var objRespuesta = objGson.fromJson(respuesta, Models.AnswerLogin::class.java)

                if(objRespuesta.acceso == "Ok"){
                    runOnUiThread {
                        //Toast.makeText(this@MainActivity, "Acceso correcto", Toast.LENGTH_SHORT).show()

                        var intento = Intent(this@MainActivity,NavActivity::class.java).apply {
                            putExtra("VAR_TOKEN", objRespuesta.token)
                        }
                        startActivity(intento)
                    }
                }else{
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, objRespuesta.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}