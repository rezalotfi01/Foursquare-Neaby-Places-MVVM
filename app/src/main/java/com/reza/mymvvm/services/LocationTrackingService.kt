package com.reza.mymvvm.services

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import com.reza.mymvvm.MainActivity
import com.reza.mymvvm.R
import com.reza.mymvvm.util.*
import com.reza.mymvvm.util.extensions.logDebug
import com.reza.mymvvm.util.extensions.sendLocalBroadcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class LocationTrackingService : Service() {

    var isTrackingEnabled = false

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return

            val location = locationResult.lastLocation

            var locationName = ""
            CoroutineScope(Dispatchers.IO).launch {
                locationName = getAddressFromLatLng(applicationContext,location.latitude,location.longitude)

                CoroutineScope(Dispatchers.Main).launch {
                    // Send location data to UI
                    val actionIntent = Intent(ACTION_LOCATION_BROADCAST).apply {
                        putExtra(INTENT_LAT, location.latitude)
                        putExtra(INTENT_LONG, location.longitude)
                        putExtra(INTENT_LOC_NAME,locationName)
                    }
                    sendLocalBroadcast(actionIntent)
                }

            }
        }
    }

    private val locationRequest: LocationRequest by lazy {
        LocationRequest.create()
            .setInterval(INTERVAL)
            .setFastestInterval(FASTEST_INTERVAL)
            .setSmallestDisplacement(SMALLEST_DISPLACEMENT)
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action == SERVICE_ACTION_STOP) {
            stopForeground(true)
            stopSelf()
        } else if (isTrackingEnabled.not()) {
            startInForeground()
            startLocationUpdates()
        }

        return START_STICKY
    }


    private fun startInForeground() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.app_running))
            .setNotificationSilent()
            .setTicker("")
            .setContentIntent(pendingIntent)
        val notification: Notification = builder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = NOTIFICATION_CHANNEL_DESC
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        startForeground(NOTIFICATION_ID, notification)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
            isTrackingEnabled = true
            logDebug("Start Location Updates")
    }

    private fun stopLocationUpdates() {
        if (isTrackingEnabled) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            isTrackingEnabled = false
            logDebug("Stop Location Updates")
        }
    }


    fun getAddressFromLatLng(context: Context?, lat: Double, lng: Double): String {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(context, Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(lat, lng, 1)
            addresses[0].getAddressLine(0)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
    }

    companion object {
        const val INTERVAL = 15 * 1000L
        const val FASTEST_INTERVAL = INTERVAL / 2
        const val SMALLEST_DISPLACEMENT = 100f

        const val NOTIFICATION_CHANNEL_ID = "MY_APP_CHANNEL_ID"
        const val NOTIFICATION_CHANNEL_NAME = "MY_APP_CHANNEL"
        const val NOTIFICATION_ID = 3458
        const val NOTIFICATION_CHANNEL_DESC = "Some Test Description"
    }
}