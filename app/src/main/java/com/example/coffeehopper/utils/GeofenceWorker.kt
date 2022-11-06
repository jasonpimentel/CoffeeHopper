package com.example.coffeehopper.utils

import android.content.Context
import androidx.work.*
import com.example.coffeehopper.datalayer.repository.CoffeeHopperRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GeofenceWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams), KoinComponent {

    private val coffeeHopperRepository: CoffeeHopperRepository by inject()

    companion object {
        const val geoFenceHops = "geoFenceHops"
        fun buildWorkRequest(coffeeHops: Array<String>): OneTimeWorkRequest {
            val data = Data.Builder().putStringArray(geoFenceHops, coffeeHops).build()
            return OneTimeWorkRequestBuilder<GeofenceWorker>().apply { setInputData(data) }.build()
        }
    }

    override fun doWork(): Result {
        val coffeeHopIDs: Array<String>? = inputData.getStringArray(geoFenceHops)

        return try {
            coffeeHopIDs?.let {
                sendNotifications(coffeeHopIDs)
            }
            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }

    private fun sendNotifications(coffeeHops: Array<String>) {

        coffeeHops.forEach { coffeeHopId ->
            val coffeeHop = coffeeHopperRepository.getCoffeeHop(coffeeHopId)
            sendNotification(context, coffeeHop)
        }
    }
}
