package com.example.asistencia.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistencia.R
import com.example.asistencia.models.Fichaje
import com.example.asistencia.models.Historial

class HistorialAdapter(
    private val context: Context,
    private val historial: List<Historial>
) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
        val layoutFichajes: LinearLayout = view.findViewById(R.id.layoutFichajes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item_historial, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = historial.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dia = historial[position]
        holder.tvFecha.text = "🗓️ ${dia.fecha}"
        holder.layoutFichajes.removeAllViews()

        // Mostrar cada entrada/salida
        for (fichaje in dia.fichajes) {
            val textView = TextView(context)
            textView.text = "🟢 Entrada: ${fichaje.entrada}    🔴 Salida: ${fichaje.salida}"
            textView.setTextColor(Color.WHITE)
            textView.textSize = 16f
            holder.layoutFichajes.addView(textView)
        }

        // Mostrar total del día
        val totalView = TextView(context)
        totalView.text = "⏱️ Total: ${dia.totalHoras}"
        totalView.setTextColor(Color.WHITE)
        totalView.setTypeface(null, Typeface.BOLD)
        totalView.setPadding(0, 12, 0, 0)
        holder.layoutFichajes.addView(totalView)
    }
}