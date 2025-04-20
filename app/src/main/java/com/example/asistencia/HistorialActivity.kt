package com.example.asistencia

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistencia.adapters.HistorialAdapter
import com.example.asistencia.models.Historial
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistorialActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_historial)

        recyclerView = findViewById(R.id.recyclerHistorial)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val empleadoId = intent.getLongExtra("empleadoId", -1L)

        if (empleadoId == -1L) {
            Toast.makeText(this, "Empleado inválido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        RetrofitClient.instance.getHistorial(empleadoId)
            .enqueue(object : Callback<List<Historial>> {
                override fun onResponse(
                    call: Call<List<Historial>>,
                    response: Response<List<Historial>>
                ) {
                    if (response.isSuccessful) {
                        val historial = response.body() ?: emptyList()
                        adapter = HistorialAdapter(this@HistorialActivity, historial)
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(this@HistorialActivity, "Error en respuesta", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Historial>>, t: Throwable) {
                    Toast.makeText(this@HistorialActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}