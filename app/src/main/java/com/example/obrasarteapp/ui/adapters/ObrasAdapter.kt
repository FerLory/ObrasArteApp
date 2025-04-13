package com.example.obrasarteapp.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.obrasarteapp.data.remote.model.ObraDto
import com.example.obrasarteapp.databinding.ObraElementBinding

class ObrasAdapter(
    private val obras: List<ObraDto>,
    private val onItemClick: (ObraDto) -> Unit
) : RecyclerView.Adapter<ObrasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObrasViewHolder {
        val binding = ObraElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObrasViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ObrasViewHolder, position: Int) {
        holder.bind(obras[position])
    }

    override fun getItemCount(): Int = obras.size
}