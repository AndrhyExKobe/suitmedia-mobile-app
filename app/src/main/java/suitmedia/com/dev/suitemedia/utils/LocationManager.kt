package suitmedia.com.dev.suitemedia.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.*
import suitmedia.com.dev.suitemedia.core.extension.isLocationPermissionGranted
import java.util.concurrent.TimeUnit


/**
 * Created by Andri Dwi Utomo on 8/11/2021.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
class LocationManager(private val context: Context?, private val listener: Listener?) :
    LifecycleObserver {

    interface Listener {
        fun onLocationUpdated(location: Location)
    }

    private val fusedLocationProviderClient: FusedLocationProviderClient? by lazy {
        context?.let { LocationServices.getFusedLocationProviderClient(it) }
    }

    private lateinit var locationCallback: LocationCallback

    private val locationResult by lazy {
        LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(10)
            fastestInterval = TimeUnit.SECONDS.toMillis(5)
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumed() {
        if (context?.isLocationPermissionGranted() == false) return

        requestLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { listener?.onLocationUpdated(it) }
            }
        }

        fusedLocationProviderClient?.lastLocation?.addOnSuccessListener { location ->
            location?.let { listener?.onLocationUpdated(it) }
        }

        fusedLocationProviderClient?.requestLocationUpdates(locationResult, locationCallback, Looper.getMainLooper())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPaused() {
        if (context?.isLocationPermissionGranted() == false) return

        fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }
}