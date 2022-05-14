package suitmedia.com.dev.suitemedia.ui.screenfive

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import suitmedia.com.dev.suitemedia.R
import suitmedia.com.dev.suitemedia.core.extension.isLocationPermissionGranted
import suitmedia.com.dev.suitemedia.core.fragment.BaseViewBindingFragment
import suitmedia.com.dev.suitemedia.databinding.FragmentScreenFiveBinding
import suitmedia.com.dev.suitemedia.ui.screenthree.dummy.Event
import suitmedia.com.dev.suitemedia.utils.CustomSetting


/**
 * Created by Andri Dwi Utomo on 14/5/2022.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class ScreenFiveFragment : BaseViewBindingFragment<FragmentScreenFiveBinding>(), OnMapReadyCallback {
    companion object {
        const val TAG = "OpenMapDialog"
        private const val REQ_PERMISSION_LOCATION = 1001
    }

    private lateinit var mMap: GoogleMap


    override fun doViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentScreenFiveBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestLocationPermission()

        val supportMapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
        supportMapFragment!!.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mapEvent(0.0,0.0)
    }

    private fun mapEvent(lat: Double, long: Double) {
        var listEvent = Event.listEvent

        mMap.clear()

        listEvent.mapIndexed { i, event ->
            val point = LatLng(event.lat, event.long)
            val marker = MarkerOptions()
            marker.position(point)
            marker.title(event.title)
            if (lat == 0.0 && long == 0.0) {
                if (i == 0) {
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_selected))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 15F))
                    binding.tvTitle.text = event.title
                    binding.tvDesc.text = event.desc
                    binding.tvDate.text = event.date
                    binding.tvTime.text = event.time
                    binding.image = context?.resources?.getDrawable(event.img)
                } else {
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_unselected))
                }
            } else {
                if (lat == event.lat && long == event.long) {
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_selected))
                } else {
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_unselected))
                }

            }

            mMap.addMarker(marker)

            mMap.setOnMarkerClickListener(OnMarkerClickListener { marker ->

                var getEvent = listEvent.find { it.lat == marker.position.latitude && it.long == marker.position.longitude }
                binding.tvTitle.text = getEvent?.title
                binding.tvDesc.text = getEvent?.desc
                binding.tvDate.text = getEvent?.date
                binding.tvTime.text = getEvent?.time
                binding.image = context?.resources?.getDrawable(getEvent?.img!!)
                mapEvent(marker.position.latitude, marker.position.longitude)
                false
            })
        }
    }

    private fun requestLocationPermission() {
        if (!requireContext().isLocationPermissionGranted()) {
            val relationalFine = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            val relationalCoarse = ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (relationalFine || relationalCoarse) {
                Toast.makeText(requireContext(), "Location permission needed", Toast.LENGTH_SHORT).show()
            }
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQ_PERMISSION_LOCATION
            )
        }
    }

    /*private fun configureMapView(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync {
            it.uiSettings.isMyLocationButtonEnabled = false
            it.uiSettings.isTiltGesturesEnabled = false
            it.uiSettings.isRotateGesturesEnabled = false
            it.uiSettings.isCompassEnabled = false
            it.setPadding(0, 0, 0, resources.getDimensionPixelSize(R.dimen.sizeMarginSmall))
            it.setOnMarkerClickListener {
                true
            }
            map = it
            locationManager.requestLocationUpdates()
        }
    }*/

    /*private fun updateMapView(latlng: LatLng) {
        if (::map.isInitialized) {
            map.clear()
            launch(Dispatchers.Default) {
                val drawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_marker_selected,
                    requireContext().theme
                )
                drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

                val bitmap = Bitmap.createBitmap(
                    drawable?.intrinsicWidth ?: 0,
                    drawable?.intrinsicHeight ?: 0,
                    Bitmap.Config.ARGB_8888
                )
                Canvas(bitmap).also { drawable?.draw(it) }
                val marker = MarkerOptions()
                marker.icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                marker.position(latlng)

                withContext(Dispatchers.Main) {
                    map.addMarker(marker)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18f))
                }
            }
        }
    }*/


}