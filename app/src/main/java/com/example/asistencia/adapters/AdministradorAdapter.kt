package com.example.asistencia.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.asistencia.AdministradoresActivity
import com.example.asistencia.R
import com.example.asistencia.models.Administrador
import com.example.asistencia.EditarAdminActivity
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdministradorAdapter(
    private val context: Context,
    private var administradores: List<Administrador>
) : BaseAdapter() {

    override fun getCount(): Int = administradores.size
    override fun getItem(position: Int): Any = administradores[position]
    override fun getItemId(position: Int): Long = administradores[position].id ?: -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_administrador, parent, false)
        val admin = administradores[position]

        val tvUsuario = view.findViewById<TextView>(R.id.tvUsuario)
        val btnEditar = view.findViewById<Button>(R.id.btnEditarAdmin)
        val btnEliminar = view.findViewById<Button>(R.id.btnEliminarAdmin)

        tvUsuario.text = admin.usuario

        btnEliminar.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Eliminar al administrador '${admin.usuario}'?")
                .setPositiveButton("Sí") { _, _ ->
                    RetrofitClient.instance.deleteAdministrador(admin.id!!).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Toast.makeText(context, "Administrador eliminado", Toast.LENGTH_SHORT).show()
                            (context as? AdministradoresActivity)?.cargarAdministradores() // actualiza la lista
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                .setNegativeButton("No", null)
                .show()
        }

        btnEditar.setOnClickListener {
            val intent = Intent(context, EditarAdminActivity::class.java)
            intent.putExtra("adminId", admin.id)
            context.startActivity(intent)
        }

        return view
    }

    fun actualizarLista(nuevaLista: List<Administrador>) {
        administradores = nuevaLista
        notifyDataSetChanged()
    }
}