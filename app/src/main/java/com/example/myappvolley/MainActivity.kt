package com.example.myappvolley

import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.myappvolley.BaseDatos.adminBD
import com.example.myappvolley.Volley.VolleySingleton

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun insertaProducto(v:View){
        if (etNombprod.text.toString().isEmpty()|| etExistencia.text.toString().isEmpty()||etPrecio.text.toString().isEmpty()){
            Toast.makeText(this, "Falta Informacion de Capturar", Toast.LENGTH_LONG).show();
           // etIdprod.requestFocus()
        }
        else{
            var jsonEntrada = JSONObject()
            jsonEntrada.put("nomProd", etNombprod.text.toString())
            jsonEntrada.put("existencia", etExistencia.text.toString())
            jsonEntrada.put("precio", etPrecio.text.toString())
            sendRequest(Adress.IP+"WSAndroid/insertProducto.php", jsonEntrada)
        }
    }

    fun actualizaProducto(v:View){
        if (etIdprod.text.toString().isEmpty()||etNombprod.text.toString().isEmpty()|| etExistencia.text.toString().isEmpty()||etPrecio.text.toString().isEmpty()){
            Toast.makeText(this, "Falta Informacion de Capturar", Toast.LENGTH_LONG).show();
            // etIdprod.requestFocus()
        }
        else{
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idprod", etIdprod.text.toString())
            jsonEntrada.put("nomProd", etNombprod.text.toString())
            jsonEntrada.put("existencia", etExistencia.text.toString())
            jsonEntrada.put("precio", etPrecio.text.toString())
            sendRequest(Adress.IP+"WSAndroid/updateProductos.php", jsonEntrada)
        }
    }
    fun eliminaProducto(v:View){
        if (etIdprod.text.toString().isEmpty()){
            Toast.makeText(this, "Falta Informacion de Capturar", Toast.LENGTH_LONG).show();
            // etIdprod.requestFocus()
        }
        else{
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idprod", etIdprod.text.toString())
            sendRequest(Adress.IP +"WSAndroid/deleteProducto.php", jsonEntrada)
        }
    }

    fun getProducto(v:View){
        if (etIdprod.text.toString().isEmpty()){
            Toast.makeText(this, "Ingresa la Clave del Producto a Buscar ", Toast.LENGTH_LONG).show();
            etIdprod.requestFocus()
        }
        else{
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idprod", etIdprod.text.toString())
            getProductoByID(jsonEntrada)
           // sendRequest(Adress.IP +"WSAndroid/getProducto.php", jsonEntrada)
        }
    }
    fun getProdusctos(v:View){
        getAllProductos()
    }

    fun getAllProductos(){
        val wsURL = Adress.IP +"WSAndroid/getProductos.php"
        val admin = adminBD(this)
        admin.Ejecuta("DELETE FROM productos")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,null,
            Response.Listener { response ->
                val succ= response["success"]
                val msg = response["message"]
                val productosJson = response.getJSONArray("productos")
                for(i in 0 until productosJson.length()){
                    val idp = productosJson.getJSONObject(i).getString("idprod")
                    val nom = productosJson.getJSONObject(i).getString("nomProd")
                    val exis = productosJson.getJSONObject(i).getString("existencia")
                    val precio = productosJson.getJSONObject(i).getString("precio")
                    val sentencia = "Insert into productos(idprod,nomProd,existencia,precio) values(${idp},'${nom}', ${exis},${precio})"
                    var res =admin.Ejecuta(sentencia)
                    Toast.makeText(this, "jelou "+ res, Toast.LENGTH_SHORT).show();


                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error getProductoBtID: ${error.message}", Toast.LENGTH_LONG).show();
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }
    fun getProductoByID(jsonEnt:JSONObject){
        val wsURL = Adress.IP +"WSAndroid/getProducto.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,jsonEnt,
            Response.Listener { response ->
                val succ= response["success"]
                val msg = response["message"]
                val productosJson = response.getJSONArray("productos")
                if (productosJson .length()>0){
                    val idp = productosJson.getJSONObject(0).getString("idprod")
                    val nom = productosJson.getJSONObject(0).getString("nomProd")
                    val exis = productosJson.getJSONObject(0).getString("existencia")
                    val precio = productosJson.getJSONObject(0).getString("precio")
                    etNombprod.setText(nom)
                    etExistencia.setText(exis)
                    etPrecio.setText(precio)
                    etIdprod.requestFocus()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error getProductoBtID: ${error.message}", Toast.LENGTH_LONG).show();
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun Consultar(v: View){
        var actividad = Intent(this,MainActivityRecycler::class.java)
        startActivity(actividad)
    }



fun sendRequest(wsURL: String,jsonEntrada: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEntrada,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                Toast.makeText(this, "Success:${succ}  Message:${msg}", Toast.LENGTH_LONG).show();
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_LONG).show();
                Log.d("ERROR","${error.message}")
                Toast.makeText(this, "API: Error de capa 8 en WS ):", Toast.LENGTH_SHORT).show();
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
