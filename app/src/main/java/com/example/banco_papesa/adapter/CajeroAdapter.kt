package com.example.banco_papesa.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.banco_papesa.R
import com.example.banco_papesa.databinding.ItemMovimientoBinding
import com.example.banco_papesa.pojo.CajeroEntity


class CajeroAdapter(private var lista: MutableList<CajeroEntity>,
                    private val listener: OnClickListener

) : RecyclerView.Adapter<CajeroAdapter.ViewHolder>() {
    val elementosSeleccionados = mutableSetOf<Any>()
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = ItemMovimientoBinding.bind(view) //Vinculamos la vista a nuestro adapter

        fun setListener(store : CajeroEntity){
            itemView.setBackgroundColor(Color.TRANSPARENT)
            binding.root.setOnClickListener {
                if (elementosSeleccionados.isEmpty() ) {
                    listener.onItemClick(store)
                } else selectItem()
            }
            itemView.setOnLongClickListener {
                selectItem()
                true // Importante: indica que el clic largo está manejado
            }

        }

        fun selectItem() {
            if (elementosSeleccionados.contains(lista[adapterPosition])) {
                // Restaurar apariencia predeterminada
                itemView.setBackgroundColor(Color.TRANSPARENT)
                elementosSeleccionados.remove(lista[adapterPosition])
            } else {
                // Aplicar efecto visual a elementos seleccionados
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.color_principal_diluido))
                elementosSeleccionados.add(lista[adapterPosition])
            }
            listener.onSelectedItem()
        }

        fun clearVisualSelected() {

        }


    }

    private lateinit var mContext: Context

    // El layout manager invoca este método para renderizar cada elemento del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //Inflar la vista item_tarea en el Recycler
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_cajero, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //Asignamos el contenido a cada item del Layout Item.xml
        val cajero = lista[position] as CajeroEntity

        with(holder){
            setListener(cajero)
            binding.lblTitulo.text = "Cajero $position"
            binding.lblSubtitulo.text = cajero.direccion

        }


    }

    // Este método devolverá el tamaño de la lista
    override fun getItemCount(): Int = lista.size
    fun add(store: CajeroEntity) {
        lista.add(store)
        notifyItemInserted(lista.size-1) //Refresca la vista del adaptador
    }

    fun setCajero(cajero: MutableList<CajeroEntity>) {
        lista = cajero
        notifyDataSetChanged()
    }

    fun update(original: CajeroEntity, editado: CajeroEntity) {

        val index = lista.indexOf(original)
        Log.i("Indice update", index.toString())
        if (index != -1) {
            lista[index] = editado
            notifyDataSetChanged()

        }
    }

    fun delete(obj: CajeroEntity) {

        val index = lista.indexOf(obj)

        if (index != -1) {
            lista.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun deleteSelectedItems() {
        for (elemento in elementosSeleccionados) {
            delete(elemento as CajeroEntity)
        }
    }


}