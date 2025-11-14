package mods.audio.enhancement

import kotlin.math.pow

/**
 * Voice Changer Plugin for Bluecord
 * Real-time pitch shifting and voice effects
 */
class VoiceChangerPlugin {

    var isEnabled: Boolean = true
    protected var sampleRate: Int = 48000

    var pitchShift: Float = 0f // in semitones
        set(value) {
            field = value.coerceIn(-12f, 12f)
            updatePitchRatio()
        }

    var formantShift: Float = 0f
        set(value) {
            field = value.coerceIn(-12f, 12f)
        }

    private var pitchRatio = 1f
    private val buffer = mutableListOf<Float>()
    private var bufferIndex = 0

    data class VoiceEffect(
        val name: String,
        val pitch: Float,
        val formant: Float,
        val description: String
    )

    val effects = mapOf(
        "none" to VoiceEffect("Normal", 0f, 0f, "No voice change"),
        "robot" to VoiceEffect("Robot", -2f, -3f, "Robotic voice"),
        "chipmunk" to VoiceEffect("Chipmunk", 7f, 5f, "High-pitched squeaky voice"),
        "deep" to VoiceEffect("Deep Voice", -5f, -4f, "Lower, deeper voice"),
        "demon" to VoiceEffect("Demon", -8f, -6f, "Dark, demonic voice"),
        "radio" to VoiceEffect("Radio", 0f, -1f, "Radio transmission"),
        "telephone" to VoiceEffect("Telephone", 1f, 2f, "Phone call quality"),
        "cave" to VoiceEffect("Cave Echo", -1f, 0f, "Echoey cave effect")
    )

    fun initialize(sampleRate: Int) {
        this.sampleRate = sampleRate
        updatePitchRatio()
    }

    private fun updatePitchRatio() {
        pitchRatio = 2f.pow(pitchShift / 12f)
    }

    fun process(input: FloatArray): FloatArray {
        if (pitchShift == 0f && formantShift == 0f) {
            return input
        }

        // Simple pitch shifting using resampling
        // Note: This is a basic implementation. Production would use phase vocoder
        return pitchShiftSimple(input)
    }

    private fun pitchShiftSimple(input: FloatArray): FloatArray {
        if (pitchRatio == 1f) return input

        val output = FloatArray(input.size)
        var readPos = 0f

        for (i in output.indices) {
            val index = readPos.toInt()
            val frac = readPos - index

            if (index < input.size - 1) {
                // Linear interpolation
                output[i] = input[index] * (1f - frac) + input[index + 1] * frac
            } else if (index < input.size) {
                output[i] = input[index]
            }

            readPos += pitchRatio
            if (readPos >= input.size) {
                readPos = input.size - 1f
            }
        }

        return applyFormantShift(output)
    }

    private fun applyFormantShift(input: FloatArray): FloatArray {
        if (formantShift == 0f) return input

        // Simple formant shifting using filtering
        // This is a simplified implementation
        val output = FloatArray(input.size)
        val shiftFactor = 2f.pow(formantShift / 12f)

        for (i in input.indices) {
            output[i] = input[i] * shiftFactor.coerceIn(0.5f, 2f)
        }

        return output
    }

    fun applyEffect(effectName: String): Boolean {
        val effect = effects[effectName] ?: return false
        pitchShift = effect.pitch
        formantShift = effect.formant
        return true
    }

    fun getCurrentSettings() = mapOf(
        "pitch" to pitchShift,
        "formant" to formantShift
    )

    fun release() {
        // Cleanup resources if needed
    }
}
