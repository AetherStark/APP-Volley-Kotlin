package com.example.myappvolley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myappvolley.BaseDatos.adminBD
import kotlinx.android.synthetic.main.activity_main_recycler.*

class MainActivityRecycler : AppCompatActivity() {

    private lateinit var viewAdapter: ProductosAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    val productoList: List<productos> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_recycler)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProductosAdapter(productoList, this, { estud: productos -> onItemClickListener(estud) })

        rv_productos.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivityRecycler, DividerItemDecoration.VERTICAL))
        }

        // Metodo para implementar la eliminación de un estudiante, cuando el ususario da un onswiped en
        // el recyclerview
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                val prod= viewAdapter.getTasks()
                val admin = adminBD(baseContext)
                if (admin.Ejecuta("DELETE FROM Estudiante WHERE noControl=" + prod[position].idP) ){
                    retrieveProductos()
                }
            }
        }).attachToRecyclerView(rv_productos)
    }

    // Evento clic cuando damos clic en un elemento del Recyclerview
    private fun onItemClickListener(prod: productos) {
        Toast.makeText(this, "Clicked item" + prod.nomProd, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        retrieveProductos()
    }

    private fun retrieveProductos() {
        val productoex = getProductos()
        viewAdapter.setTask(productoex!!)
    }

    fun getProductos(): MutableList<productos>{
        var productos:MutableList<productos> = ArrayList()
        val admin = adminBD(this)

        //                                          0       1       2      3
        val tupla = admin.Consulta("SELECT idprod,nomProd,existencia,precio FROM productos ORDER BY nomProd")
        while (tupla!!.moveToNext()) {
            val idp = tupla.getInt(0)
            val nomP = tupla.getString(1)
            val exi = tupla.getInt(2)
            val prec = tupla.getFloat(3)

            productos.add(productos(idp,nomP,exi,prec))
        }
        tupla.close()
        admin.close()
        return productos
    }
}

