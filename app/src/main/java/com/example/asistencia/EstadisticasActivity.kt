//package com.example.asistencia
//
//
//
//import android.os.Build
//import android.os.Bundle
//import android.widget.*
//import androidx.annotation.RequiresApi
//import androidx.appcompat.app.AppCompatActivity
//import com.example.asistencia.models.Empleado
//import com.example.asistencia.models.Estadisticas
//import com.example.asistencia.services.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.time.LocalDate
//
//class EstadisticasActivity : AppCompatActivity() {
//
//    private lateinit var spinner: Spinner
//    private lateinit var tvHoras: TextView
//    private lateinit var tvAsistidos: TextView
//    private lateinit var tvFaltados: TextView
//    private lateinit var tvResumen: TextView
//    private lateinit var btnHistorial: Button
//    private var empleados: List<Empleado> = listOf()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.layout_estadisticas)
//
//        spinner = findViewById(R.id.spinnerEmpleados)
//        tvHoras = findViewById(R.id.tvHorasTrabajadas)
//        tvAsistidos = findViewById(R.id.tvDiasAsistidos)
//        tvFaltados = findViewById(R.id.tvDiasFaltados)
//        tvResumen = findViewById(R.id.tvResumenSemanal)
//        btnHistorial = findViewById(R.id.btnVerHistorial)
//
//        cargarEmpleados()
//
//        btnHistorial.setOnClickListener {
//            if (empleadoSeleccionadoId != null) {
//                val intent = Intent(this, HistorialActivity::class.java)
//                intent.putExtra("empleadoId", empleadoSeleccionadoId)
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, "Selecciona un empleado", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    private fun cargarEmpleados() {
//        RetrofitClient.instance.getEmpleados().enqueue(object : Callback<List<Empleado>> {
//            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
//                empleados = response.body() ?: emptyList()
//                val nombres = empleados.map { "${it.nombre} ${it.apellido}" }
//                val adapter = ArrayAdapter(this@EstadisticasActivity, android.R.layout.simple_spinner_dropdown_item, nombres)
//                spinner.adapter = adapter
//
//                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
//                        val empleado = empleados[position]
//                        cargarEstadisticas(empleado.id!!)
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>) {}
//                }
//            }
//
//            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
//                Toast.makeText(this@EstadisticasActivity, "Error al cargar empleados", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun cargarEstadisticas(empleadoId: Long) {
//        RetrofitClient.instance.obtenerEstadisticasEmpleado(empleadoId)
//            .enqueue(object : Callback<Estadisticas> {
//                @RequiresApi(Build.VERSION_CODES.O)
//                override fun onResponse(call: Call<Estadisticas>, response: Response<Estadisticas>) {
//                    val estadisticas = response.body()
//                    if (estadisticas != null) {
//                        tvHoras.text = "🕒 Total horas trabajadas: ${estadisticas.horasTrabajadas}"
//                        tvAsistidos.text = "✅ Días asistidos: ${estadisticas.diasAsistidos}"
//                        tvFaltados.text = "❌ Días faltados: ${estadisticas.diasFaltados}"
//
//                        val resumen = estadisticas.resumenSemanal.map {
//                            val localDate = LocalDate.parse(it.key)
//                            val dia = String.format("%02d/%02d", localDate.dayOfMonth, localDate.monthValue)
//                            if (it.value) "✅ $dia" else "❌ $dia"
//                        }.joinToString("  ")
//
//                        tvResumen.text = "🗓️ Resumen semanal:\n$resumen"
//                    }
//                }
//
//                override fun onFailure(call: Call<Estadisticas>, t: Throwable) {
//                    Toast.makeText(this@EstadisticasActivity, "Error al obtener estadísticas", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//}


package com.example.asistencia

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.models.Empleado
import com.example.asistencia.models.Estadisticas
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class EstadisticasActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var tvHoras: TextView
    private lateinit var tvAsistidos: TextView
    private lateinit var tvFaltados: TextView
    private lateinit var tvResumen: TextView
    private lateinit var btnHistorial: Button
    private var empleados: List<Empleado> = listOf()
    private var empleadoSeleccionadoId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_estadisticas)

        spinner = findViewById(R.id.spinnerEmpleados)
        tvHoras = findViewById(R.id.tvHorasTrabajadas)
        tvAsistidos = findViewById(R.id.tvDiasAsistidos)
        tvFaltados = findViewById(R.id.tvDiasFaltados)
        tvResumen = findViewById(R.id.tvResumenSemanal)
        btnHistorial = findViewById(R.id.btnVerHistorial)

        cargarEmpleados()

        btnHistorial.setOnClickListener {
            if (empleadoSeleccionadoId != null) {
                val intent = Intent(this, HistorialActivity::class.java)
                intent.putExtra("empleadoId", empleadoSeleccionadoId)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un empleado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun cargarEmpleados() {
        RetrofitClient.instance.getEmpleados().enqueue(object : Callback<List<Empleado>> {
            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
                empleados = response.body() ?: emptyList()
                val nombres = empleados.map { "${it.nombre} ${it.apellido}" }
                val adapter = ArrayAdapter(this@EstadisticasActivity, android.R.layout.simple_spinner_dropdown_item, nombres)
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                        val empleado = empleados[position]
                        empleadoSeleccionadoId = empleado.id
                        cargarEstadisticas(empleado.id!!)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            }

            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
                Toast.makeText(this@EstadisticasActivity, "Error al cargar empleados", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarEstadisticas(empleadoId: Long) {
        RetrofitClient.instance.obtenerEstadisticasEmpleado(empleadoId)
            .enqueue(object : Callback<Estadisticas> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<Estadisticas>, response: Response<Estadisticas>) {
                    val estadisticas = response.body()
                    if (estadisticas != null) {
                        tvHoras.text = "🕒 Total horas trabajadas: ${estadisticas.horasTrabajadas}"
                        tvAsistidos.text = "✅ Días asistidos: ${estadisticas.diasAsistidos}"
                        tvFaltados.text = "❌ Días faltados: ${estadisticas.diasFaltados}"

                        val resumen = estadisticas.resumenSemanal.map {
                            val localDate = LocalDate.parse(it.key)
                            val dia = String.format("%02d/%02d", localDate.dayOfMonth, localDate.monthValue)
                            if (it.value) "✅ $dia" else "❌ $dia"
                        }.joinToString("  ")

                        tvResumen.text = "🗓️ Resumen semanal:\n$resumen"
                    }
                }

                override fun onFailure(call: Call<Estadisticas>, t: Throwable) {
                    Toast.makeText(this@EstadisticasActivity, "Error al obtener estadísticas", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
