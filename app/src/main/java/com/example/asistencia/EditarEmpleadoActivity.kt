package com.example.asistencia

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.models.Empleado
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarEmpleadoActivity : AppCompatActivity() {

    private var empleadoId: Long? = null
    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etDni: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_editar_empleado)

        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etDni = findViewById(R.id.etDni)
        btnGuardar = findViewById(R.id.btnGuardarEmpleado)

        empleadoId = intent.getLongExtra("empleadoId", -1)
        if (empleadoId == -1L) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
        }

        obtenerEmpleado(empleadoId!!)

        btnGuardar.setOnClickListener {
            val empleado = Empleado(
                etNombre.text.toString(),
                etApellido.text.toString(),
                etDni.text.toString(),
                empleadoId
            )

            RetrofitClient.instance.updateEmpleado(empleadoId!!, empleado)
                .enqueue(object : Callback<Empleado> {
                    override fun onResponse(call: Call<Empleado>, response: Response<Empleado>) {
                        Toast.makeText(this@EditarEmpleadoActivity, "Empleado actualizado", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onFailure(call: Call<Empleado>, t: Throwable) {
                        Toast.makeText(this@EditarEmpleadoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun obtenerEmpleado(id: Long) {
        RetrofitClient.instance.getEmpleadoById(id).enqueue(object : Callback<Empleado> {
            override fun onResponse(call: Call<Empleado>, response: Response<Empleado>) {
                val emp = response.body()
                if (emp != null) {
                    etNombre.setText(emp.nombre)
                    etApellido.setText(emp.apellido)
                    etDni.setText(emp.dni)
                }
            }

            override fun onFailure(call: Call<Empleado>, t: Throwable) {
                Toast.makeText(this@EditarEmpleadoActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}
