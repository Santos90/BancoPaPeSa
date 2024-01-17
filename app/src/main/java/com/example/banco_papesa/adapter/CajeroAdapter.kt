package com.example.banco_papesa.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.banco_papesa.R
import com.example.banco_papesa.databinding.ItemMovimientoBinding
import com.example.banco_papesa.pojo.CajeroEntity


class CajeroAdapter(private var lista: MutableList<CajeroEntity>,
                    private val listener: OnClickListener

) : RecyclerView.Adapter<CajeroAdapter.ViewHolder>() {

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val binding = ItemMovimientoBinding.bind(view) //Vinculamos la vista a nuestro adapter

        fun setListener(store : CajeroEntity){
            binding.root.setOnClickListener {
                listener.onClick(store)
            }

        }


    }

    private lateinit var mContext: Context

    // El layout manager invoca este método para renderizar cada elemento del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //Inflar la vista item_tarea en el Recycler
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_movimiento, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { //Asignamos el contenido a cada item del Layout Item.xml
        val cajero = lista[position] as CajeroEntity

        with(holder){
            setListener(cajero)
            binding.lblTitulo.text = "ATM $position"
            binding.lblSubtitulo.text = cajero.direccion

            Glide.with(mContext)
                .load(R.drawable.ic_bank)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.imagen)

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

        if (index != -1) {
            lista[index] = editado
            notifyItemChanged(index)

        }
    }

    fun delete(obj: CajeroEntity) {

        val index = lista.indexOf(obj)

        if (index != -1) {
            lista.removeAt(index)
            notifyItemRemoved(index)
        }
    }


}