package mods.audio.enhancement

import kotlin.math.*

/**
 * Audio Effects Plugin for Bluecord
 * Reverb, echo, chorus, and distortion effects
 */
class AudioEffectsPlugin {

    var isEnabled: Boolean = true
    protected var sampleRate: Int = 48000

    var reverbEnabled = false
    var echoEnabled = false
    var chorusEnabled = false
    var distortionEnabled = false

    // Reverb parameters
    var reverbMix = 0.3f
    private val reverbBuffer = FloatArray(48000) // 1 second buffer
    private var reverbIndex = 0

    // Echo parameters
    var echoTime = 0.3f // seconds
    var echoFeedback = 0.4f
    private var echoBuffer = FloatArray(48000)
    private var echoIndex = 0
    private var echoDelaySamples = 0

    // Chorus parameters
    private var chorusPhase = 0f
    private val chorusRate = 1.5f
    private val chorusDepth = 0.005f

    // Distortion parameters
    var distortionAmount = 50f

    fun initialize(sampleRate: Int) {
        this.sampleRate = sampleRate
        echoBuffer = FloatArray((sampleRate * 2.0).toInt())
        updateEchoDelay()
    }

    private fun updateEchoDelay() {
        echoDelaySamples = (echoTime * sampleRate).toInt()
    }

    fun process(input: FloatArray): FloatArray {
        var output = input.copyOf()

        if (distortionEnabled) {
            output = applyDistortion(output)
        }

        if (echoEnabled) {
            output = applyEcho(output)
        }

        if (chorusEnabled) {
            output = applyChorus(output)
        }

        if (reverbEnabled) {
            output = applyReverb(output)
        }

        return output
    }

    private fun applyReverb(input: FloatArray): FloatArray {
        val output = FloatArray(input.size)

        for (i in input.indices) {
            // Simple reverb using comb filter
            val delayed = reverbBuffer[reverbIndex]
            reverbBuffer[reverbIndex] = input[i] + delayed * 0.5f

            output[i] = input[i] * (1f - reverbMix) + delayed * reverbMix

            reverbIndex = (reverbIndex + 1) % reverbBuffer.size
        }

        return output
    }

    private fun applyEcho(input: FloatArray): FloatArray {
        val output = FloatArray(input.size)

        for (i in input.indices) {
            val readIndex = (echoIndex - echoDelaySamples + echoBuffer.size) % echoBuffer.size
            val delayed = echoBuffer[readIndex]

            echoBuffer[echoIndex] = input[i] + delayed * echoFeedback

            output[i] = input[i] + delayed * 0.5f

            echoIndex = (echoIndex + 1) % echoBuffer.size
        }

        return output
    }

    private fun applyChorus(input: FloatArray): FloatArray {
        val output = FloatArray(input.size)

        for (i in input.indices) {
            // LFO for chorus
            val lfo = sin(chorusPhase) * chorusDepth * sampleRate
            chorusPhase += 2f * PI.toFloat() * chorusRate / sampleRate

            if (chorusPhase >= 2f * PI) {
                chorusPhase -= 2f * PI.toFloat()
            }

            // Simple delay modulation
            output[i] = input[i] * 0.7f + (if (i > lfo.toInt()) input[i - lfo.toInt()] else input[i]) * 0.3f
        }

        return output
    }

    private fun applyDistortion(input: FloatArray): FloatArray {
        val output = FloatArray(input.size)
        val k = distortionAmount

        for (i in input.indices) {
            val x = input[i]
            // Soft clipping distortion
            output[i] = ((3f + k) * x * 20f * (PI.toFloat() / 180f)) /
                    (PI.toFloat() + k * abs(x))
        }

        return output
    }

    fun setEchoTime(time: Float) {
        echoTime = time.coerceIn(0.01f, 1f)
        updateEchoDelay()
    }

    fun setEchoFeedback(feedback: Float) {
        echoFeedback = feedback.coerceIn(0f, 0.9f)
    }

    fun setReverbMix(mix: Float) {
        reverbMix = mix.coerceIn(0f, 1f)
    }

    fun getAvailableEffects() = listOf("reverb", "echo", "chorus", "distortion")

    fun release() {
        reverbBuffer.fill(0f)
        echoBuffer.fill(0f)
    }
}
