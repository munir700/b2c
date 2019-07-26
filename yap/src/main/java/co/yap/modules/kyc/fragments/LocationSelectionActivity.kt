package co.yap.modules.kyc.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.yap.R
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


//class LocationSelectionActivity : AppCompatActivity(), OnMapReadyCallback {
//    companion object {
//
//
//        fun newIntent(context: Context): Intent = Intent(context, LocationSelectionActivity::class.java)
//
//    }
//
//    private lateinit var mMap: GoogleMap
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//    }
//}

class LocationSelectionActivity : AppCompatActivity()  {
         companion object {


            fun newIntent(context: Context): Intent = Intent(context, LocationSelectionActivity::class.java)
             // Used to load the 'native-lib' library on application startup.
//             init {
//                 System.loadLibrary("native-lib")
//             }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as MyMapFragment
        /*mapFragment.initial_latitude = -10.0
        mapFragment.initial_longitude = 115.0
        mapFragment.initial_marker = "Inishol mawker"*/
        mapFragment.getMapAsync(mapFragment)
        System.err.println("OnCreate end")
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

}
