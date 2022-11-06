package com.example.coffeehopper.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.example.coffeehopper.R
import com.example.coffeehopper.presentationlayer.activities.CoffeeDetailsActivity
import com.example.coffeehopper.presentationlayer.fragments.CoffeeFavoritesFragment
import com.example.coffeehopper.presentationlayer.fragments.CoffeeListFragment
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import timber.log.Timber

class GeoFenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent: GeofencingEvent = GeofencingEvent.fromIntent(intent)!!

        // check for error
        if (geofencingEvent.hasError()) {
            Timber.d(errorMessage(context, geofencingEvent.errorCode))
            return
        }
        // check for action
        if (intent.action != CoffeeDetailsActivity.addGeofenceAction) {
            return
        }

        if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            if (!geofencingEvent.triggeringGeofences.isNullOrEmpty()) {
                val map: List<String>? = geofencingEvent.triggeringGeofences?.map { geofence ->
                    geofence.requestId
                }

                WorkManager.getInstance(context)
                    .enqueue(GeofenceWorker.buildWorkRequest(map!!.toTypedArray()))

            } else {
                Timber.d("no geofence trigger found")
            }
        }

    }

    private fun errorMessage(context: Context, errorCode: Int): String {
        val resources = context.resources
        return when (errorCode) {
            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> resources.getString(
                R.string.geofence_not_available
            )
            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> resources.getString(
                R.string.geofence_too_many_geofences
            )
            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> resources.getString(
                R.string.geofence_too_many_pending_intents
            )
            else -> resources.getString(R.string.unknown_geofence_error)
        }
    }
}