package com.example.obrasarteapp.ui.fragments

import android.os.Bundle
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

class ObrasListFragment : Fragment() {

    private var _binding: FragmentObrasListBinding? = null
    private val binding get() = _binding!!

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

                binding.rvObras.layoutManager = LinearLayoutManager(requireContext())
                binding.rvObras.adapter = ObrasAdapter(obras) { obraSeleccionada ->
                    val action = ObrasListFragmentDirections
                        .actionObrasListFragmentToDetalleObraFragment(obraSeleccionada)
                    findNavController().navigate(action)
                }

                binding.rvObras.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_carga_obras), Toast.LENGTH_SHORT).show()
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