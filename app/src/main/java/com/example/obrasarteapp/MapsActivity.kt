package com.example.obrasarteapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val toolbar = findViewById<Toolbar>(R.id.mapsToolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val latitud = intent.getDoubleExtra(getString(R.string.latitudM), 19.4326)
        val longitud = intent.getDoubleExtra(getString(R.string.longitudM), -99.1332)
        val nombreLugar = intent.getStringExtra(getString(R.string.nombrelugarM)) ?: getString(R.string.ubicacion_de_la_obraM)

        val latLng = LatLng(latitud, longitud)
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_marker_arte)
        val smallBitmap = Bitmap.createScaledBitmap(originalBitmap, 96, 96, false)
        val markerIcon = BitmapDescriptorFactory.fromBitmap(smallBitmap)

        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(nombreLugar)
                .icon(markerIcon)
        )

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }
}