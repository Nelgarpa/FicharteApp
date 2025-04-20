package com.example.asistencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.adapters.AdministradorAdapter
import com.example.asistencia.models.Administrador
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdministradoresActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: AdministradorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_lista_administradores)

        listView = findViewById(R.id.listaAdministradores)
        adapter = AdministradorAdapter(this, emptyList())
        listView.adapter = adapter

        // 👉 Conectar el botón +
        val btnAgregar = findViewById<Button>(R.id.btnAgregarAdmin)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarAdminActivity::class.java)
            startActivity(intent)
        }

        cargarAdministradores()
    }

    override fun onResume() {
        super.onResume()
        cargarAdministradores() // 🔁 Refresca la lista cuando vuelve de agregar/editar
    }

    fun cargarAdministradores() {
        RetrofitClient.instance.getAdministradores().enqueue(object :
            Callback<List<Administrador>> {
            override fun onResponse(
                call: Call<List<Administrador>>,
                response: Response<List<Administrador>>
            ) {
                if (response.isSuccessful) {
                    adapter.actualizarLista(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<Administrador>>, t: Throwable) {
                Toast.makeText(this@AdministradoresActivity, "Error al obtener administradores", Toast.LENGTH_SHORT).show()
            }
        })
    }
}