package com.example.myappvolley

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.celda_prototipo_productos.view.*

class ProductosAdapter (private var mListaProductos:List<productos>,
                        private val mContext: Context, private val clickListener: (productos) -> Unit)
    : RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        return ProductosViewHolder(layoutInflater.inflate(R.layout.celda_prototipo_productos, parent, false))
    }

    /**
     * La clase RecyclerView. onBindViewHolder() se encarga de coger cada una de las
     * posiciones de la lista de estudiantes y pasarlas a la clase ViewHolder(
     *
     * @param holder   Vincular los datos del cursor al ViewHolder
     * @param position La posición de los datos en la lista
     */
    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        holder.bind(mListaProductos[position], mContext, clickListener)
    }

    /**
     * El método getItemCount() nos devuelve el tamaño de la lista, que lo necesita
     * el RecyclerView.
     */
    override fun getItemCount(): Int = mListaProductos.size

    /**
     * Cuando los datos cambian, este metodo actualiza la lista de estudiantes
     * y notifica al adaptador a usar estos nuevos valores
     */
    fun setTask(productos: List<productos>){
        mListaProductos = productos
        notifyDataSetChanged()
    }

    fun getTasks(): List<productos> = mListaProductos

    /**
     * Clase interna para crear ViewHolders
     */
    class ProductosViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView) {

        fun bind (prod:productos, context: Context, clickListener: (productos) -> Unit){
            //Asigna los valores a los elementos del la celda_prototipo_estudiante
            itemView.tvNom.text = prod.nomProd
            itemView.tvExist.text = prod.existen.toString()

            itemView.setOnClickListener{ clickListener(prod)}
        }
    }

}


