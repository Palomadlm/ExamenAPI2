package com.unirfp.examenapi2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.unirfp.examenapi2.databinding.ItemproductoBinding
import com.unirfp.examenapi2.model.Producto

class ProductoViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemproductoBinding.bind(view)

    fun bind(producto: Producto){
        Picasso.get().load(producto.image).into(binding.ivProducto)
        binding.tvName.setText(producto.name.uppercase())
        binding.tvDescription.setText(producto.description)
        binding.tvPrice.text = String.format("%.2f €", producto.price)

    }
}