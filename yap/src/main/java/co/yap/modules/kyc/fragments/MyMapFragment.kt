package co.yap.modules.kyc.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import co.yap.R
import co.yap.yapcore.BaseBindingFragment
import co.yap.yapcore.defaults.DefaultViewModel
import co.yap.yapcore.defaults.IDefault
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MyMapFragment : BaseBindingFragment<IDefault.ViewModel>(), IDefault.View, OnMapReadyCallback {
//    override fun onMapReady(p0: GoogleMap?) {
    //    }

    private lateinit var mMap: GoogleMap

    var initial_latitude  = 25.276987
    var initial_longitude = 55.296249
    var initial_marker = "theMarkerLocation"

    override fun getBindingVariable(): Int {
        return 0
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_maps
    }

    override val viewModel: IDefault.ViewModel
        get() = ViewModelProviders.of(this).get(DefaultViewModel::class.java)


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
////        var mMapFragment = SupportMapFragment()
////
////        mMapFragment = object : SupportMapFragment() {
////            override fun onActivityCreated(savedInstanceState: Bundle?) {
////                super.onActivityCreated(savedInstanceState)
//////                mMap = mMapFragment.getMapAsync()
//////                if (mMap != null) {
//////                    setupMap()
//////                }
////            }
////        }
////        childFragmentManager.beginTransaction().add(R.id.framelayout_location_container, mMapFragment).commit()
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mMap =
//        (map as SupportMapFragment).getMapAsync(this)

        // Check if we were successful in obtaining the map.
//        if (mMap != null) {
//        mapFragment.getMapAsync(mapFragment)
//            setUpMap()
//        }

        var mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        /*mapFragment.initial_latitude = -10.0
        mapFragment.initial_longitude = 115.0
        mapFragment.initial_marker = "Inishol mawker"*/
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        System.err.println("OnMapReady start")
        mMap = map as GoogleMap;

        val sydney = LatLng(initial_latitude, initial_longitude);
        mMap.addMarker(MarkerOptions().position(sydney).title("LOC"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        Toast.makeText(this.context, "OnMapReady end", Toast.LENGTH_LONG).show()
        setUpMap()
    }
//    if (mMap) {
    // Try to obtain the map from the SupportMapFragment.
//        mMap = (getActivity().getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment).getMap()
//        // Check if we were successful in obtaining the map.
//        if (mMap != null) {
//            setUpMap()
//        }
//    }


    /** * This is where we can add markers or lines, add listeners or move the camera. In this case, we * just add a marker near Africa. *
     *
     * * This should only be called once and when we are sure that [.mMap] is not null.  */
    private fun setUpMap() {
        mMap!!.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker"))
    }

}

//class MapFragment : Fragment() {
//    private var mMap: GoogleMap? = null
//    fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View {
//        return inflater.inflate(R.layout.fragment_map, container, false)
//    }
//
//    fun onActivityCreated(savedInstanceState: Bundle) {
//        super.onActivityCreated(savedInstanceState)
//        setUpMapIfNeeded()
//    }
//
//    fun onResume() {
//        super.onResume()
//        setUpMapIfNeeded()
//    }
//
//    private fun setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = (getActivity().getSupportFragmentManager().findFragmentById(R.id.map) as SupportMapFragment).getMap()
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap()
//            }
//        }
//    }
//
//    /** * This is where we can add markers or lines, add listeners or move the camera. In this case, we * just add a marker near Africa. *
//     *
//     * * This should only be called once and when we are sure that [.mMap] is not null.  */
//    private fun setUpMap() {
//        mMap!!.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Marker"))
//    }
//}