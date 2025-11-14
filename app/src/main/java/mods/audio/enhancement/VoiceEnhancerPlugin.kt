package mods.audio.enhancement

import kotlin.math.abs
import kotlin.math.pow

/**
 * Voice Enhancer Plugin for Bluecord
 * Provides noise reduction and voice clarity improvements
 */
class VoiceEnhancerPlugin {

    var isEnabled: Boolean = true
    protected var sampleRate: Int = 48000

    var gain: Float = 1.0f
        set(value) {
            field = value.coerceIn(0f, 2f)
        }

    var noiseThreshold: Float = -50f
        set(value) {
            field = value.coerceIn(-100f, 0f)
        }

    // Noise gate parameters
    private var threshold = 0.01f
    private var ratio = 12f
    private var attack = 0.001f
    private var release = 0.25f
    private var envelope = 0f

    fun initialize(sampleRate: Int) {
        this.sampleRate = sampleRate
        updateThreshold()
    }

    private fun updateThreshold() {
        threshold = 10f.pow(noiseThreshold / 20f)
    }

    fun process(input: FloatArray): FloatArray {
        val output = FloatArray(input.size)

        for (i in input.indices) {
            val sample = input[i]
            val amplitude = abs(sample)

            // Apply noise gate
            if (amplitude > threshold) {
                envelope += attack * (1f - envelope)
            } else {
                envelope *= (1f - release)
            }

            // Apply gain and envelope
            output[i] = sample * gain * envelope
        }

        return output
    }

    fun setGainDb(db: Float) {
        gain = 10f.pow(db / 20f)
    }

    fun release() {
        // Cleanup resources if needed
    }
}
