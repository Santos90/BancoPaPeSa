package com.example.banco_papesa.pojo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.banco_papesa.OnClickListener
import com.example.banco_papesa.R
import com.example.banco_papesa.databinding.ItemCuentaBinding
import com.example.bancoapiprofe.pojo.Cuenta


class CuentaAdapter(private val lista: ArrayList<*>, private val listener: OnClickListener):
    RecyclerView.Adapter<CuentaAdapter.ViewHolder>(){

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = ItemCuentaBinding.bind(view) //Vinculamos la vista a nuestro adapter

        fun setListener(cuenta : Any){
            binding.root.setOnClickListener {listener.onClick(cuenta)}
        }

    }

    private lateinit var context: Context

    // El layout manager invoca este método para renderizar cada elemento del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //Inflar la vista item_tarea en el Recycler
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_cuenta, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //Asignamos el contenido a cada item del Layout Item.xml
        val cuenta = lista.get(position) as Cuenta

        with(holder){
            setListener(cuenta)
            binding.lblTitulo.text = cuenta.getNumeroCuenta()
            binding.lblSubtitulo.text = cuenta.getSaldoActual().toString()

        }

        Glide.with(context)
            .load(R.drawable.ic_bank)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .into(holder.binding.imagen)

    }

    // Este método devolverá el tamaño de la lista
    override fun getItemCount(): Int = lista.size



}