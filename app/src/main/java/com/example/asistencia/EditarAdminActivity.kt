package com.example.asistencia

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistencia.models.Administrador
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarAdminActivity : AppCompatActivity() {

    private var adminId: Long? = null
    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_editar_admin)

        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnGuardar = findViewById(R.id.btnGuardar)

        adminId = intent.getLongExtra("adminId", -1)
        if (adminId == -1L) {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
        }

        obtenerDatosAdmin(adminId!!)

        btnGuardar.setOnClickListener {
            val usuario = etUsuario.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val adminActualizado = Administrador(null ,usuario, password)
            adminActualizado.id = adminId

            RetrofitClient.instance.actualizarAdministrador(adminId!!, adminActualizado)
                .enqueue(object : Callback<Administrador> {
                    override fun onResponse(call: Call<Administrador>, response: Response<Administrador>) {
                        Toast.makeText(this@EditarAdminActivity, "Administrador actualizado", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onFailure(call: Call<Administrador>, t: Throwable) {
                        Toast.makeText(this@EditarAdminActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

    private fun obtenerDatosAdmin(id: Long) {
        RetrofitClient.instance.getAdministradorById(id).enqueue(object : Callback<Administrador> {
            override fun onResponse(call: Call<Administrador>, response: Response<Administrador>) {
                val admin = response.body()
                if (admin != null) {
                    etUsuario.setText(admin.usuario)
                    etPassword.setText(admin.password)
                }
            }

            override fun onFailure(call: Call<Administrador>, t: Throwable) {
                Toast.makeText(this@EditarAdminActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}