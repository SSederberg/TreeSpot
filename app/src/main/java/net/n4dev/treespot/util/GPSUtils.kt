package net.n4dev.treespot.util

import android.content.Context
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task

class GPSUtils {
    companion object {
        private val highAccuracyRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        private val locationSettingsBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(highAccuracyRequest)

        fun checkSettings(context: Context) : Task<LocationSettingsResponse> {
            return LocationServices.getSettingsClient(context).checkLocationSettings(
                locationSettingsBuilder.build())
        }

        fun getLocationClient(context: Context) : FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(context)
        }
    }
}