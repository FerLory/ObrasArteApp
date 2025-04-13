package com.example.obrasarteapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.obrasarteapp.R
import com.example.obrasarteapp.databinding.FragmentDetalleObraBinding

class DetalleObraFragment : Fragment() {

    private var _binding: FragmentDetalleObraBinding? = null
    private val binding get() = _binding!!

    private val args: DetalleObraFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleObraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val obra = args.obra

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(requireContext())
            .load(obra.imagen)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.ivObraDetalle)

        binding.tvTituloDetalle.text = obra.nombre
        binding.tvAutorDetalle.text = getString(R.string.label_autor, obra.autor)
        binding.tvAnioDetalle.text = getString(R.string.label_anio, obra.anio.toString())
        binding.tvEstilo.text = getString(R.string.label_estilo, obra.estilo)
        binding.tvUbicacion.text = getString(R.string.label_ubicacion, obra.ubicacion)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}