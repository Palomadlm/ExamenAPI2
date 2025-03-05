package com.unirfp.examenapi2

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.unirfp.examenapi2.databinding.ActivityMainBinding
import com.unirfp.examenapi2.model.Producto
import com.unirfp.examenapi2.model.ProductosResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductoAdapter
    private val productoList = mutableListOf<Producto>() // Listado con todos los productos


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        // Inicialización del binding y la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización RecyclerView
        initRecyclerView()

        // Método para obtener todos los productos
        getProductos("products")

        // Configuración del buscador
        binding.svProductos.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {

                    adapter.updateList(productoList)
                } else {
                    val filteredList = productoList.filter { producto ->
                        producto.name?.contains(newText, ignoreCase = true) == true
                    }
                    adapter.updateList(filteredList)
                }
                return true
            }
        })
    }


    private fun initRecyclerView() {
        adapter = ProductoAdapter(productoList)
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://peticiones.online/api/") // URL base para la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getProductos(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<ProductosResponse> = getRetrofit()
                .create(ApiService::class.java)
                .getAllProductos()

            val productos: ProductosResponse? = call.body()
            if (productos != null) {
                for (miProducto: Producto in productos.results) {
                    Log.v("Productos", miProducto.toString())
                }
            }


            runOnUiThread {
                if (call.isSuccessful) {
                    val productosL = productos?.results ?: emptyList()
                    productoList.clear()
                    productoList.addAll(productosL)
                    adapter.notifyDataSetChanged()


                } else {
                    showError()
                }

            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
}