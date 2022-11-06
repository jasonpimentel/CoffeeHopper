package com.example.coffeehopper.presentationlayer.activities

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.coffeehopper.R
import com.example.coffeehopper.databinding.ActivityCoffeeDetailsBinding
import com.example.coffeehopper.datalayer.database.CoffeeHop
import com.example.coffeehopper.presentationlayer.viewmodels.CoffeeDetailsViewModel
import com.example.coffeehopper.utils.GeoFenceBroadcastReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CoffeeDetailsActivity : AppCompatActivity() {

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeoFenceBroadcastReceiver::class.java)
        intent.action = addGeofenceAction
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private val viewModel: CoffeeDetailsViewModel by viewModel()

    private lateinit var geofencingClient: GeofencingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCoffeeDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_coffee_details)
        intent.extras?.let { ext ->
            viewModel.coffeeHop = ext.getParcelable(CoffeeHop.extraName)
            binding.coffeeHop = viewModel.coffeeHop
        }

        viewModel.favorited.observe(this) {
            if (it) {
                viewModel.addFavorite(viewModel.coffeeHop!!)
                var geofence = buildGeofence(viewModel.coffeeHop!!)
                var geoFenceRequest = buildGeofenceRequest(geofence)
                addGeofence(geoFenceRequest)
            } else {
                viewModel.removeFavorite(viewModel.coffeeHop!!)
                removeGeofence(viewModel.coffeeHop!!.id)
            }
        }

        binding.favoriteToggle.setOnClickListener {
            viewModel.setFavorited((it as ToggleButton).isChecked)
        }

        geofencingClient = LocationServices.getGeofencingClient(this)
    }

    private fun buildGeofence(coffeeHop: CoffeeHop): Geofence {
        return Geofence.Builder()
            .setCircularRegion(coffeeHop.latitude!!, coffeeHop.longitude!!, 100f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setRequestId(coffeeHop.id)
            .setExpirationDuration(TimeUnit.DAYS.toMillis(365))
            // check every hour for better battery
            // .setNotificationResponsiveness(3600000)
            .setNotificationResponsiveness(TimeUnit.SECONDS.toMillis(1).toInt())
            .build()
    }

    private fun buildGeofenceRequest(geofence: Geofence): GeofencingRequest {
        return GeofencingRequest.Builder()
            .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
            .addGeofence(geofence).build()
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence(geofenceRequest: GeofencingRequest) {
        geofencingClient.addGeofences(geofenceRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Toast.makeText(
                    this@CoffeeDetailsActivity,
                    "geofence created", Toast.LENGTH_LONG
                )
                    .show()
            }

            addOnFailureListener {
                Toast.makeText(
                    this@CoffeeDetailsActivity,
                    "problem with adding geofence",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }

    private fun removeGeofence(coffeeHopID: String) {
        geofencingClient.removeGeofences(listOf(coffeeHopID)).run {
            addOnSuccessListener {
                Toast.makeText(
                    this@CoffeeDetailsActivity,
                    "geofence removed", Toast.LENGTH_LONG
                )
                    .show()
            }

            addOnFailureListener {
                Toast.makeText(
                    this@CoffeeDetailsActivity,
                    "problem removing geofence", Toast.LENGTH_LONG
                )
                    .show()
            }
        }
    }


    companion object {
        const val addGeofenceAction = "add geofence"
        const val removeGeofenceAction = "remove geofence"

    }
}