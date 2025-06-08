package com.example.obrasarteapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.obrasarteapp.MapsActivity
import com.example.obrasarteapp.R
import com.example.obrasarteapp.application.ObrasArteApp
import com.example.obrasarteapp.data.remote.model.ObraDto
import com.example.obrasarteapp.databinding.FragmentDetalleObraBinding
import kotlinx.coroutines.launch

class DetalleObraFragment : Fragment() {

    private var _binding: FragmentDetalleObraBinding? = null
    private val binding get() = _binding!!
    private val args: DetalleObraFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleObraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val obraId = args.obraId
        val repository = (requireActivity().application as ObrasArteApp).repository

        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val obras = repository.getObras()
                val obra = obras.find { it.id == obraId }

                if (obra == null) {
                    Toast.makeText(requireContext(),
                        getString(R.string.obra_no_encontrada), Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    return@launch
                }

                setupActionBar()
                cargarImagen(obra)
                mostrarDatos(obra)
                configurarWebView(obra.videoUrl)

                binding.btnVerMapa.setOnClickListener {
                    val intent = Intent(requireContext(), MapsActivity::class.java)
                    intent.putExtra(getString(R.string.latitud), obra.latitud)
                    intent.putExtra(getString(R.string.longitud), obra.longitud)
                    intent.putExtra(getString(R.string.ubicacion), obra.ubicacion)
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(),
                    getString(R.string.error_al_cargar_la_obra), Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
    }

    private fun cargarImagen(obra: ObraDto) {
        Glide.with(requireContext())
            .load(obra.imagen)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(binding.ivObraDetalle)
    }

    private fun mostrarDatos(obra: ObraDto) {
        binding.tvTituloDetalle.text = obra.nombre
        binding.tvAutorDetalle.text = getString(R.string.label_autor, obra.autor)
        binding.tvAnioDetalle.text = getString(R.string.label_anio, obra.anio)
        binding.tvEstilo.text = getString(R.string.label_estilo, obra.estilo)
        binding.tvUbicacion.text = getString(R.string.label_ubicacion, obra.ubicacion)
    }

    private fun configurarWebView(videoUrl: String) {
        binding.progressBar.visibility = View.VISIBLE

        binding.webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.visibility = View.GONE
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, context.getString(R.string.error_al_cargar_el_video), Toast.LENGTH_SHORT).show()
                }
            }

            try {
                val videoId = videoUrl.split("v=")[1].split("&")[0]
                loadUrl("https://www.youtube.com/embed/$videoId")
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(context, context.getString(R.string.url_de_video_inv_lida), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        binding.webView.stopLoading()
        binding.webView.destroy()
        _binding = null
        super.onDestroyView()
    }
}