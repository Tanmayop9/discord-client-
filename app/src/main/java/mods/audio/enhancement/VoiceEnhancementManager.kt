package mods.audio.enhancement

import android.media.audiofx.AudioEffect
import android.media.audiofx.Equalizer
import kotlin.math.*

/**
 * Voice Enhancement Manager for Bluecord
 * Integrates voice enhancement, EQ, voice changer, and effects
 */
class VoiceEnhancementManager(private val audioSessionId: Int) {

    // Plugins
    val voiceEnhancer = VoiceEnhancerPlugin()
    val equalizer = EqualizerPlugin(audioSessionId)
    val voiceChanger = VoiceChangerPlugin()
    val audioEffects = AudioEffectsPlugin()

    var isEnabled = true
    private val sampleRate = 48000

    init {
        initialize()
    }

    private fun initialize() {
        voiceEnhancer.initialize(sampleRate)
        equalizer.initialize()
        voiceChanger.initialize(sampleRate)
        audioEffects.initialize(sampleRate)
    }

    /**
     * Process audio buffer through enhancement pipeline
     */
    fun processAudio(inputBuffer: ShortArray): ShortArray {
        if (!isEnabled) return inputBuffer

        var buffer = inputBuffer.copyOf()

        // Convert to float for processing
        val floatBuffer = FloatArray(buffer.size) { buffer[it] / 32768f }

        // Apply plugins in order
        var processed = floatBuffer

        if (voiceEnhancer.isEnabled) {
            processed = voiceEnhancer.process(processed)
        }

        if (voiceChanger.isEnabled) {
            processed = voiceChanger.process(processed)
        }

        if (audioEffects.isEnabled) {
            processed = audioEffects.process(processed)
        }

        // Convert back to short
        return ShortArray(processed.size) {
            (processed[it] * 32768f).toInt().coerceIn(-32768, 32767).toShort()
        }
    }

    /**
     * Release all resources
     */
    fun release() {
        voiceEnhancer.release()
        equalizer.release()
        voiceChanger.release()
        audioEffects.release()
    }

    /**
     * Enable/disable all enhancements
     */
    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }
}
