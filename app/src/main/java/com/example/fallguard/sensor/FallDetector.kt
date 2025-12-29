package com.example.fallguard.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class FallDetector(
    context: Context,
    private val onFallDetected: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var freefallStartTime: Long = 0
    private var isFreefall = false

    private val FREEFALL_THRESHOLD = 2.5f
    private val IMPACT_THRESHOLD = 20.0f
    private val FREEFALL_TIME_MS = 150

    fun start() {
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val magnitude = sqrt(x * x + y * y + z * z)

            if (magnitude < FREEFALL_THRESHOLD) {
                if (!isFreefall) {
                    isFreefall = true
                    freefallStartTime = System.currentTimeMillis()
                }
            } else if (isFreefall) {
                val freefallDuration = System.currentTimeMillis() - freefallStartTime

                if (freefallDuration > FREEFALL_TIME_MS && magnitude > IMPACT_THRESHOLD) {
                    onFallDetected()
                }
                isFreefall = false
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}