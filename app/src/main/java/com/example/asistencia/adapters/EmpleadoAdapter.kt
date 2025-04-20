package com.example.asistencia.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.*
import com.example.asistencia.*
import com.example.asistencia.models.Empleado
import com.example.asistencia.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpleadoAdapter(
    private val context: Context,
    private var empleados: List<Empleado>
) : BaseAdapter() {

    override fun getCount(): Int = empleados.size
    override fun getItem(position: Int): Any = empleados[position]
    override fun getItemId(position: Int): Long = empleados[position].id ?: -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_empleado, parent, false)
        val empleado = empleados[position]

        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val tvDni = view.findViewById<TextView>(R.id.tvDni)
        val btnEditar = view.findViewById<Button>(R.id.btnEditar)
        val btnEliminar = view.findViewById<Button>(R.id.btnEliminar)

        tvNombre.text = "${empleado.nombre} ${empleado.apellido}"
        tvDni.text = empleado.dni

        tvNombre.text = "${empleado.nombre} ${empleado.apellido}"

        btnEditar.setOnClickListener {
            val intent = Intent(context, EditarEmpleadoActivity::class.java)
            intent.putExtra("empleadoId", empleado.id)
            context.startActivity(intent)
        }

        btnEliminar.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Eliminar al empleado ${empleado.nombre} ${empleado.apellido}?")
                .setPositiveButton("Sí") { _, _ ->
                    RetrofitClient.instance.deleteEmpleado(empleado.id!!).enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            Toast.makeText(context, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                            (context as? EmpleadosActivity)?.cargarEmpleados()
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
                .setNegativeButton("No", null)
                .show()
        }

        return view
    }

    fun actualizarLista(nuevaLista: List<Empleado>) {
        empleados = nuevaLista
        notifyDataSetChanged()
    }
}