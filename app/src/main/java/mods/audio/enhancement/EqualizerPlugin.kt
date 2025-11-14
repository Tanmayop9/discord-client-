package mods.audio.enhancement

import android.media.audiofx.Equalizer

/**
 * Equalizer Plugin for Bluecord
 * Uses Android's native Equalizer API with custom presets
 */
class EqualizerPlugin(audioSessionId: Int) {

    var isEnabled: Boolean = true
    private var equalizer: Equalizer? = null
    private var numBands: Short = 0

    val presets = mapOf(
        "flat" to floatArrayOf(0f, 0f, 0f, 0f, 0f),
        "bass_boost" to floatArrayOf(8f, 6f, 4f, 2f, 0f),
        "treble_boost" to floatArrayOf(0f, 2f, 4f, 6f, 8f),
        "voice_clarity" to floatArrayOf(-2f, 0f, 4f, 2f, 0f),
        "deep_voice" to floatArrayOf(6f, 4f, 0f, -2f, -1f),
        "bright_voice" to floatArrayOf(-2f, 0f, 4f, 6f, 4f),
        "radio" to floatArrayOf(-4f, 4f, 6f, 2f, -2f),
        "podcast" to floatArrayOf(2f, 2f, 4f, 2f, 0f)
    )

    fun initialize() {
        try {
            equalizer = Equalizer(0, audioSessionId).apply {
                enabled = true
            }
            numBands = equalizer?.numberOfBands ?: 5
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Set gain for a specific band
     * @param bandIndex Band index (0-based)
     * @param gainDb Gain in decibels
     */
    fun setBandGain(bandIndex: Int, gainDb: Float) {
        try {
            equalizer?.let { eq ->
                if (bandIndex in 0 until numBands) {
                    val minLevel = eq.bandLevelRange[0]
                    val maxLevel = eq.bandLevelRange[1]
                    val level = (gainDb * 100).toInt().coerceIn(minLevel.toInt(), maxLevel.toInt()).toShort()
                    eq.setBandLevel(bandIndex.toShort(), level)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Apply a preset configuration
     */
    fun applyPreset(name: String) {
        presets[name]?.forEachIndexed { index, gain ->
            if (index < numBands) {
                setBandGain(index, gain)
            }
        }
    }

    /**
     * Get current band levels
     */
    fun getBands(): FloatArray {
        return FloatArray(numBands.toInt()) { index ->
            try {
                equalizer?.getBandLevel(index.toShort())?.toFloat() ?: 0f
            } catch (e: Exception) {
                0f
            } / 100f
        }
    }

    /**
     * Reset to flat response
     */
    fun reset() {
        applyPreset("flat")
    }

    /**
     * Get frequency for a band
     */
    fun getCenterFreq(band: Short): Int {
        return try {
            equalizer?.getCenterFreq(band) ?: 0
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Get number of bands
     */
    fun getNumberOfBands() = numBands.toInt()

    fun release() {
        equalizer?.release()
        equalizer = null
    }
}
