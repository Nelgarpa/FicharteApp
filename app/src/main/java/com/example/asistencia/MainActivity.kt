//package com.example.asistencia
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import android.widget.*
//import androidx.activity.ComponentActivity
//import androidx.activity.enableEdgeToEdge
//
//class MainActivity : ComponentActivity() {
//    private lateinit var spinnerOpciones: Spinner
//    private lateinit var btnFichar: Button
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.layout_main)
//
//        // Inicializar las vistas correctamente
//        spinnerOpciones = findViewById(R.id.spinnerOpciones)
//        btnFichar = findViewById(R.id.btnFichar)
//        val btnAdmin = findViewById<Button>(R.id.btnAdmin)
//
//        // Lista de nombres a mostrar en el Spinner
//        val nombres = arrayOf("Selecciona un nombre","Ronald", "Valeria", "Gerardo", "Marisil", "Adrián")
//
//        // Adaptador para el Spinner
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, nombres)
//
//        // Asignar el adaptador al Spinner
//        spinnerOpciones.adapter = adapter
//
//        // Acción al seleccionar un nombre del Spinner
//        spinnerOpciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
//                val nombreSeleccionado = parent.getItemAtPosition(position).toString()
//              /*  Toast.makeText(
//                    this@MainActivity,
//                    "Seleccionaste: $nombreSeleccionado",
//                    Toast.LENGTH_SHORT
//                ).show()*/
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // No hacer nada si no se selecciona nada
//            }
//        }
//
//        // Acción al hacer clic en el botón "Fichar"
//        btnFichar.setOnClickListener {
//            val nombreSeleccionado = spinnerOpciones.selectedItem.toString()
//            if (nombreSeleccionado == "Selecciona un nombre") {
//                Toast.makeText(
//                    this@MainActivity,
//                    "Por favor, selecciona un nombre válido.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                Toast.makeText(
//                    this@MainActivity,
//                    "$nombreSeleccionado ha fichado.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//
//
//        btnAdmin.setOnClickListener {
//            val intent = Intent(this@MainActivity, AdminActivity::class.java)
//            startActivity(intent)
//        }
//
//
//    }
//}

//
//package com.example.asistencia
//
//import android.app.AlertDialog
//import android.content.Intent
//import android.os.Bundle
//import android.widget.*
//import androidx.activity.ComponentActivity
//import androidx.activity.enableEdgeToEdge
//import com.example.asistencia.models.*
//import com.example.asistencia.services.RetrofitClient
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.text.SimpleDateFormat
//import java.util.*
//
//class MainActivity : ComponentActivity() {
//
//    private lateinit var spinnerOpciones: Spinner
//    private lateinit var btnFicharEntrada: Button
//    private lateinit var btnFicharSalida: Button
//    private lateinit var btnAdmin: Button
//    private lateinit var empleados: List<Empleado>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//       // enableEdgeToEdge()
//        setContentView(R.layout.layout_main)
//
//        // Inicializar views
//        spinnerOpciones = findViewById(R.id.spinnerOpciones)
//        btnFicharEntrada = findViewById(R.id.btnFicharEntrada)
//        btnFicharSalida = findViewById(R.id.btnFicharSalida)
//        btnAdmin = findViewById(R.id.btnAdmin)
//
//        cargarEmpleados()
//
//        btnFicharEntrada.setOnClickListener {
//            val empleado = obtenerEmpleadoSeleccionado() ?: return@setOnClickListener
//            confirmarFichaje("entrada", empleado)
//        }
//
//        btnFicharSalida.setOnClickListener {
//            val empleado = obtenerEmpleadoSeleccionado() ?: return@setOnClickListener
//            confirmarFichaje("salida", empleado)
//        }
//
//        btnAdmin.setOnClickListener {
//            startActivity(Intent(this@MainActivity, AdminActivity::class.java))
//        }
//    }
//
//    private fun cargarEmpleados() {
//        RetrofitClient.instance.getEmpleados().enqueue(object : Callback<List<Empleado>> {
//            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
//                empleados = response.body() ?: emptyList()
//                val nombres = listOf("Selecciona un nombre") + empleados.map { "${it.nombre} ${it.apellido}" }
//                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, nombres)
//                spinnerOpciones.adapter = adapter
//            }
//
//            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error al cargar empleados", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun obtenerEmpleadoSeleccionado(): Empleado? {
//        val index = spinnerOpciones.selectedItemPosition
//        return if (index == 0) {
//            Toast.makeText(this, "Selecciona un empleado válido.", Toast.LENGTH_SHORT).show()
//            null
//        } else {
//            empleados[index - 1]
//        }
//    }
//
//    private fun confirmarFichaje(tipo: String, empleado: Empleado) {
//        val mensaje = if (tipo == "entrada") {
//            "¿Registrar entrada de ${empleado.nombre} ${empleado.apellido}?"
//        } else {
//            "¿Registrar salida de ${empleado.nombre} ${empleado.apellido}?"
//        }
//
//        AlertDialog.Builder(this)
//            .setTitle("Confirmar Fichaje")
//            .setMessage(mensaje)
//            .setPositiveButton("Sí") { _, _ ->
//                if (tipo == "entrada") registrarEntrada(empleado)
//                else registrarSalida(empleado)
//            }
//            .setNegativeButton("No", null)
//            .show()
//    }
//
//    private fun registrarEntrada(empleado: Empleado) {
//        val horaEntrada = obtenerHoraActualCompat()
//        val asistencia = Asistencia(empleado, horaEntrada)
//
//        RetrofitClient.instance.fichar(asistencia).enqueue(object : Callback<Asistencia> {
//            override fun onResponse(call: Call<Asistencia>, response: Response<Asistencia>) {
//                Toast.makeText(this@MainActivity, "Entrada registrada con éxito", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onFailure(call: Call<Asistencia>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error al registrar entrada: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun registrarSalida(empleado: Empleado) {
//        RetrofitClient.instance.obtenerUltimaAsistenciaSinSalida(empleado.id!!).enqueue(object : Callback<Asistencia> {
//            override fun onResponse(call: Call<Asistencia>, response: Response<Asistencia>) {
//                val asistencia = response.body()
//                if (asistencia != null) {
//                    val horaSalida = obtenerHoraActualCompat()
//                    asistencia.horaSalida = horaSalida
//
//                    RetrofitClient.instance.actualizarAsistencia(asistencia.id!!, asistencia)
//                        .enqueue(object : Callback<Asistencia> {
//                            override fun onResponse(call: Call<Asistencia>, response: Response<Asistencia>) {
//                                Toast.makeText(this@MainActivity, "Salida registrada con éxito", Toast.LENGTH_SHORT).show()
//                            }
//
//                            override fun onFailure(call: Call<Asistencia>, t: Throwable) {
//                                Toast.makeText(this@MainActivity, "Error al registrar salida", Toast.LENGTH_SHORT).show()
//                            }
//                        })
//                } else {
//                    Toast.makeText(this@MainActivity, "No hay entrada previa para este empleado", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Asistencia>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "Error al buscar asistencia: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    // Compatibilidad con minSdk 24 (sin ZonedDateTime)
//    private fun obtenerHoraActualCompat(): String {
//        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//        sdf.timeZone = TimeZone.getTimeZone("Europe/Madrid")
//        return sdf.format(Date())
//    }
//}


package com.example.asistencia

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.asistencia.models.Asistencia
import com.example.asistencia.models.Empleado
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var spinnerOpciones: Spinner
    private lateinit var btnFicharEntrada: Button
    private lateinit var btnFicharSalida: Button
    private lateinit var btnAdmin: Button
    private lateinit var empleados: List<Empleado>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)

        spinnerOpciones = findViewById(R.id.spinnerOpciones)
        btnFicharEntrada = findViewById(R.id.btnFicharEntrada)
        btnFicharSalida = findViewById(R.id.btnFicharSalida)
        btnAdmin = findViewById(R.id.btnAdmin)

        cargarEmpleados()

        btnFicharEntrada.setOnClickListener {
            val empleado = obtenerEmpleadoSeleccionado() ?: return@setOnClickListener
            confirmarFichaje("entrada", empleado)
        }

        btnFicharSalida.setOnClickListener {
            val empleado = obtenerEmpleadoSeleccionado() ?: return@setOnClickListener
            confirmarFichaje("salida", empleado)
        }

        btnAdmin.setOnClickListener {
            startActivity(Intent(this@MainActivity, AdminActivity::class.java))
        }
    }

    private fun cargarEmpleados() {
        RetrofitClient.instance.getEmpleados().enqueue(object : Callback<List<Empleado>> {
            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
                empleados = response.body() ?: emptyList()
                val nombres = listOf("Selecciona un nombre") + empleados.map { "${it.nombre} ${it.apellido}" }
                val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, nombres)
                spinnerOpciones.adapter = adapter
            }

            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error al cargar empleados", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerEmpleadoSeleccionado(): Empleado? {
        val index = spinnerOpciones.selectedItemPosition
        return if (index == 0) {
            Toast.makeText(this, "Selecciona un empleado válido.", Toast.LENGTH_SHORT).show()
            null
        } else {
            empleados[index - 1]
        }
    }

    private fun confirmarFichaje(tipo: String, empleado: Empleado) {
        val mensaje = if (tipo == "entrada") {
            "¿Registrar entrada de ${empleado.nombre} ${empleado.apellido}?"
        } else {
            "¿Registrar salida de ${empleado.nombre} ${empleado.apellido}?"
        }

        AlertDialog.Builder(this)
            .setTitle("Confirmar Fichaje")
            .setMessage(mensaje)
            .setPositiveButton("Sí") { _, _ ->
                if (tipo == "entrada") registrarEntrada(empleado)
                else registrarSalida(empleado)
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun registrarEntrada(empleado: Empleado) {
        val horaEntrada = obtenerHoraActualCompat()
        val asistencia = Asistencia(empleado, horaEntrada)

        RetrofitClient.instance.fichar(asistencia).enqueue(object : Callback<Asistencia> {
            override fun onResponse(call: Call<Asistencia>, response: Response<Asistencia>) {
                Toast.makeText(this@MainActivity, "Entrada registrada con éxito", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Asistencia>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error al registrar entrada: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registrarSalida(empleado: Empleado) {
        RetrofitClient.instance.ficharSalida(empleado.id!!).enqueue(object : Callback<Asistencia> {
            override fun onResponse(call: Call<Asistencia>, response: Response<Asistencia>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Salida registrada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "⚠️ No hay entrada previa registrada hoy", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Asistencia>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error al fichar salida: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun obtenerHoraActualCompat(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("Europe/Madrid")
        return sdf.format(Date())
    }
}