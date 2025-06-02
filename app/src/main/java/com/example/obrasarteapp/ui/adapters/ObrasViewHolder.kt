package com.example.obrasarteapp.ui.adapters

import android.media.MediaPlayer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.obrasarteapp.R
import com.example.obrasarteapp.databinding.ObraElementBinding
import com.example.obrasarteapp.data.remote.model.ObraDto

class ObrasViewHolder(
    private val binding: ObraElementBinding,
    private val onItemClick: (ObraDto) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(obra: ObraDto) {
        binding.tvTitulo.text = obra.nombre
        binding.tvAutor.text = obra.autor
        binding.tvAnio.text = obra.anio.toString()

        Glide.with(binding.root.context)
            .load(obra.imagen)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.ivObra)

        binding.root.setOnClickListener {
            // Reproducir sonido al hacer clic
            val mediaPlayer = MediaPlayer.create(binding.root.context, R.raw.ding)
            mediaPlayer.start()

            mediaPlayer.setOnCompletionListener {
                mediaPlayer.release()
            }
            onItemClick(obra)
        }
    }
}