package com.example.obrasarteapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.obrasarteapp.application.ObrasArteApp
import com.example.obrasarteapp.databinding.FragmentObrasListBinding
import com.example.obrasarteapp.ui.adapters.ObrasAdapter
import kotlinx.coroutines.launch
import com.example.obrasarteapp.R
import com.example.obrasarteapp.data.remote.model.ObraDto

class ObrasListFragment : Fragment() {

    private var _binding: FragmentObrasListBinding? = null
    private val binding get() = _binding!!


    companion object {
        var listaObrasGlobal: List<ObraDto> = emptyList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentObrasListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = (requireActivity().application as ObrasArteApp).repository

        binding.progressBar.visibility = View.VISIBLE
        binding.rvObras.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val obras = repository.getObras()


                listaObrasGlobal = obras


                obras.forEach { obra ->
                    Log.d(
                        getString(R.string.test_coords),
                        "Obra: ${obra.nombre}, lat=${obra.latitud}, lon=${obra.longitud}, ubicacion='${obra.ubicacion}'"
                    )
                }



                if (obras.isNotEmpty()) {
                    Log.d(getString(R.string.test_video), "URL del video: ${obras[0].videoUrl}")
                } else {
                    Log.e(getString(R.string.test_videos),
                        getString(R.string.la_lista_de_obras_est_vac_a))
                }

                binding.rvObras.layoutManager = LinearLayoutManager(requireContext())
                binding.rvObras.adapter = ObrasAdapter(obras) { obraSeleccionada ->

                    val action = ObrasListFragmentDirections
                        .actionObrasListFragmentToDetalleObraFragment(obraSeleccionada.id)
                    findNavController().navigate(action)
                }

                binding.rvObras.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_carga_obras), Toast.LENGTH_SHORT).show()
                Log.e(getString(R.string.error_obras), getString(R.string.error_al_obtener_obras), e)
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}