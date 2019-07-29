package co.yap.modules.kyc.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.yap.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class LocationSelectionActivity : AppCompatActivity(), OnMapReadyCallback  {

    companion object {


        fun newIntent(context: Context): Intent = Intent(context, LocationSelectionActivity::class.java)

    }

    private lateinit var mMap: GoogleMap
    private var mListener: HelperMapFragment.OnTouchListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        .setListener(() -> scrollView.requestDisallowInterceptTouchEvent(true))
//        mapFragment.getMapAsync(this)

//        { scrollView.requestDisallowInterceptTouchEvent(true) }


      var  mapFragment = (supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?)!!
        assert(fragmentManager != null)
        (supportFragmentManager.findFragmentById(R.id.map)  )
//            .setListener (  scrollView.requestDisallowInterceptTouchEvent(true)    )
//        SupportMapFragment
    }

//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        if (v == scrollView) {
//            Log.i("abcxyz", "sv t")
//            HelperMapFragment.OnTouchListener.sethis)
//            return true
//        }
//        Log.i("abcxyz", "else f")
//
//        return false
//    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(25.276987, 55.296249);
        mMap.addMarker(MarkerOptions().position(sydney).title("theMarker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Toast.makeText(this, "OnMapReady end", Toast.LENGTH_LONG).show()

    }
}
