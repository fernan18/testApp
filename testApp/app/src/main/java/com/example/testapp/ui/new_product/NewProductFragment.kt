package com.example.testapp.ui.new_product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNewProductBinding
import com.example.testapp.extras.GlobalVariables
import com.example.testapp.extras.Models
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "json_product"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewProductFragment : Fragment() {
    private var _binding: FragmentNewProductBinding? = null
    private val binding get() = _binding!!
    private var id_product:Int = 0

    // TODO: Rename and change types of parameters
    private var json_product: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            json_product = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewProductBinding.inflate(inflater, container, false)
        val view = binding.root

        if(json_product != null){
            var objGson = Gson()
            var objProduct = objGson.fromJson(json_product, Models.Product::class.java)

            id_product = objProduct.id

            binding.txtcode.editText?.setText(objProduct.code)
            binding.txtName.editText?.setText(objProduct.name)
            binding.txtPrice.editText?.setText(objProduct.price.toString())
            binding.txtStock.editText?.setText(objProduct.stock.toString())

        }
        else
        {
            binding.btnEliminar.visibility = View.GONE
        }

        binding.btnGuardar.setOnClickListener{
            var url = GlobalVariables.url_save_products

            val formBody: RequestBody = FormBody.Builder()
                .add("id", id_product.toString())
                .add("code", binding.txtcode.editText?.text.toString() )
                .add("name", binding.txtName.editText?.text.toString() )
                .add("price", binding.txtPrice.editText?.text.toString() )
                .add("stock", binding.txtStock.editText?.text.toString() )
                .build()

            val request = Request
                .Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + GlobalVariables.TOKEN)
                .post(formBody)
                .build()

            val client = OkHttpClient()
            var objGson = Gson()

            client.newCall(request).enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Fallo la peticion: " +e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    activity?.runOnUiThread{
                        findNavController().navigate(R.id.nav_home)
                    }

                    println("Guardo correctamente: " + response.body?.string())
                }

            })

        }

        binding.btnEliminar.setOnClickListener {
            if(id_product != 0 ){
                var url = GlobalVariables.url_delete_products

                val formBody: RequestBody = FormBody.Builder()
                    .add("id", id_product.toString())
                    .build()

                val request = Request
                    .Builder()
                    .url(url)
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + GlobalVariables.TOKEN)
                    .post(formBody)
                    .build()

                val client = OkHttpClient()
                var objGson = Gson()

                client.newCall(request).enqueue( object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("Fallo la peticion: " +e.message.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        activity?.runOnUiThread{
                            findNavController().navigate(R.id.nav_home)
                        }

                        println("Guardo correctamente: " + response.body?.string())
                    }

                })

            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}