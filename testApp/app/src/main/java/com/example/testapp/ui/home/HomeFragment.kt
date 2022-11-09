package com.example.testapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.databinding.FragmentHomeBinding
import com.example.testapp.extras.GlobalVariables
import com.example.testapp.extras.Models
import com.example.testapp.extras.ProductAdapter
import com.google.gson.Gson
import okhttp3.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private var TOKEN: String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        TOKEN = activity?.intent?.extras?.getString("VAR_TOKEN")
        GlobalVariables.TOKEN = TOKEN
        println("/*/*/*/*/TOKEN: "+ TOKEN)

        getProducts()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getProducts(){
        var url = GlobalVariables.url_get_products

        val request = Request.Builder()
            .url(url)
            .header("Accept", "application/json")
            .header("Authorization", "Bearer " + TOKEN)
            .get()
            .build()

        val client = OkHttpClient()
        var gson = Gson()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                activity?.runOnUiThread{
                    Toast.makeText(context, "Ocurrio un error" + e.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                var respuesta = response.body?.string()

                println("Lista respuesta: " + respuesta)

                activity?.runOnUiThread{
                    var listItem = gson.fromJson(respuesta, Array<Models.Product>::class.java)

                    val adapter = ProductAdapter(listItem);
                    binding.rvData.layoutManager = LinearLayoutManager(context)
                    binding.rvData.adapter = adapter
                }
            }
        })
    }
}