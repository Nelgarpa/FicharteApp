package com.example.asistencia

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.adapters.EmpleadoAdapter
import com.example.asistencia.models.Empleado
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpleadosActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: EmpleadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_empleados)

        listView = findViewById(R.id.listaEmpleados)
        adapter = EmpleadoAdapter(this, emptyList())
        listView.adapter = adapter

        val btnAgregar = findViewById<Button>(R.id.btnAgregarEmpleado)
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarEmpleadoActivity::class.java)
            startActivity(intent)
        }

        cargarEmpleados()
    }

    override fun onResume() {
        super.onResume()
        cargarEmpleados()
    }

    fun cargarEmpleados() {
        RetrofitClient.instance.getEmpleados().enqueue(object : Callback<List<Empleado>> {
            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
                if (response.isSuccessful) {
                    adapter.actualizarLista(response.body() ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
                Toast.makeText(this@EmpleadosActivity, "Error al obtener empleados", Toast.LENGTH_SHORT).show()
            }
        })
    }
}