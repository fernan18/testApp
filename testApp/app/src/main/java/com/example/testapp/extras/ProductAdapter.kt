package com.example.testapp.extras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.google.gson.Gson
import java.text.DecimalFormat
import java.text.NumberFormat

class ProductAdapter (private val dataSet: Array<Models.Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtNombre: TextView
        val txtPrecio: TextView

        init {
            // Define click listener for the ViewHolder's View.
            txtNombre = view.findViewById(R.id.txtName)
            txtPrecio = view.findViewById(R.id.txtPrice)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.row_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.itemView.setOnClickListener {

            var objGson = Gson()
            var json_product = objGson.toJson(dataSet[position])

            var navController = Navigation.findNavController(it)
            val bundle = bundleOf("json_product" to json_product)

            navController.navigate(R.id.nav_new_product, bundle)
        }

        viewHolder.txtNombre.text = dataSet[position]?.name
        //val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en", "US"))
        val formatter: NumberFormat = DecimalFormat("$#,##0.00")

        viewHolder.txtPrecio.text = formatter.format(dataSet[position]?.price)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}