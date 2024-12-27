package com.example.prueva_capsa

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Dashboard_CuidadorActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_cuidador)

        // Configuración del RecyclerView
        val citas = listOf(
            Cita("Cita 1", "25/12/2024, 10:00 AM", "Dirección 1"),
            Cita("Cita 2", "26/12/2024, 2:00 PM", "Dirección 2")
        )

        try {
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_citas)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = CitaAdapter(citas)
        } catch (e: Exception) {
            Log.e("RECYCLER_VIEW_ERROR", "Error al configurar el RecyclerView: ${e.message}")
            Toast.makeText(this, "Error cargando citas", Toast.LENGTH_SHORT).show()
        }

        // Configurar el fragmento del mapa
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? SupportMapFragment
        if (mapFragment != null) {
            mapFragment.getMapAsync(this)
        } else {
            Log.e("MAP_FRAGMENT_ERROR", "No se pudo cargar el fragmento del mapa")
            Toast.makeText(this, "Error cargando el mapa", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        Log.d("MAP_READY", "Mapa inicializado correctamente")

        try {
            // Configurar una ubicación inicial
            val ubicacionInicial = LatLng(19.432608, -99.133209) // Ciudad de México
            googleMap.addMarker(MarkerOptions().position(ubicacionInicial).title("Ubicación inicial"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionInicial, 15f))
        } catch (e: Exception) {
            Log.e("MAP_SETUP_ERROR", "Error al configurar el mapa: ${e.message}")
            Toast.makeText(this, "Error mostrando el mapa", Toast.LENGTH_SHORT).show()
        }
    }
}
