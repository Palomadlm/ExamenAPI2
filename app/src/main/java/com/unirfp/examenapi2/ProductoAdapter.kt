package com.unirfp.examenapi2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unirfp.examenapi2.model.Producto

class ProductoAdapter(private var productos: MutableList<Producto>) : RecyclerView.Adapter<ProductoViewHolder>(){


    // Método para actualizar la lista
    fun updateList(newList: List<Producto>) {
        productos.clear()
        productos.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(R.layout.itemproducto, parent, false))
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item : Producto = productos[position]
        holder.bind(item)
    }

}
