# Voice Enhancement Integration Guide

This document explains how the voice enhancement features are integrated into Bluecord.

## Architecture

### Directory Structure
```
app/src/main/java/mods/audio/enhancement/
├── VoiceEnhancementManager.kt    # Main manager coordinating all plugins
├── VoiceEnhancerPlugin.kt        # Noise reduction and gain control
├── EqualizerPlugin.kt            # Android native EQ integration
├── VoiceChangerPlugin.kt         # Pitch shifting and voice effects
└── AudioEffectsPlugin.kt         # Reverb, echo, chorus, distortion
```

### Plugin System

Each audio plugin:
1. Operates independently
2. Can be enabled/disabled individually
3. Processes audio in float format (-1.0 to 1.0)
4. Maintains its own state

### Audio Processing Flow

```
┌─────────────┐
│ Microphone  │
└──────┬──────┘
       │ ShortArray (PCM 16-bit)
       ▼
┌──────────────────────┐
│ Convert to Float     │
│ (normalize to -1..1) │
└──────┬───────────────┘
       │ FloatArray
       ▼
┌──────────────────────┐
│ Voice Enhancer       │
│ - Noise gate         │
│ - Gain control       │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│ Equalizer            │
│ - 5-band native EQ   │
│ - Preset support     │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│ Voice Changer        │
│ - Pitch shift        │
│ - Formant shift      │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│ Audio Effects        │
│ - Reverb/Echo        │
│ - Chorus/Distortion  │
└──────┬───────────────┘
       │ FloatArray
       ▼
┌──────────────────────┐
│ Convert to Short     │
│ (scale to -32768..   │
│  32767)              │
└──────┬───────────────┘
       │ ShortArray
       ▼
┌──────────────────────┐
│ Discord Voice Stream │
└──────────────────────┘
```

## Integration Points

### 1. Audio Session Management

The voice enhancement integrates with Discord's audio session:

```kotlin
// Get audio session ID from Discord's voice connection
val audioSessionId = discordVoiceConnection.getAudioSessionId()

// Initialize enhancement manager
val enhancementManager = VoiceEnhancementManager(audioSessionId)
```

### 2. Audio Buffer Processing

In the voice recording callback:

```kotlin
override fun onAudioDataAvailable(buffer: ShortArray) {
    // Process through enhancement pipeline
    val enhancedBuffer = enhancementManager.processAudio(buffer)
    
    // Send to Discord
    discordVoiceConnection.sendAudioData(enhancedBuffer)
}
```

### 3. Settings Integration

Settings are stored in Android SharedPreferences:

```xml
<!-- In patch/res/xml/prefs_voice_enhancement.xml -->
<PreferenceScreen>
    <PreferenceCategory android:title="Voice Enhancement">
        <!-- Settings UI -->
    </PreferenceCategory>
</PreferenceScreen>
```

Access settings in code:

```kotlin
val prefs = PreferenceManager.getDefaultSharedPreferences(context)
val enabled = prefs.getBoolean("voice_enhancer_enabled", true)
val gain = prefs.getInt("voice_enhancer_gain", 100) / 100f
```

## Performance Considerations

### CPU Usage
- Voice enhancement: ~5-10% CPU (depends on device)
- Equalizer: Native Android API (very efficient)
- Voice changer: ~10-15% CPU
- Audio effects: ~5-10% CPU per effect

Total: ~20-30% CPU with all features enabled on mid-range devices.

### Latency
- Voice enhancer: <5ms
- Equalizer: <1ms (hardware accelerated)
- Voice changer: ~10-20ms (pitch shifting)
- Audio effects: ~5-15ms per effect

Total added latency: ~20-40ms (acceptable for voice chat)

### Memory Usage
- Voice enhancer: ~1MB (buffers and state)
- Equalizer: Negligible (native)
- Voice changer: ~2MB (buffers)
- Audio effects: ~3-5MB (reverb/echo buffers)

Total: ~5-10MB additional RAM usage

## Optimization Tips

### For Better Performance
1. Disable unused effects
2. Use native Equalizer instead of software EQ
3. Reduce buffer sizes on powerful devices
4. Use hardware audio processing when available

### For Lower Latency
1. Minimize effects chain
2. Use simpler pitch shifting algorithm
3. Reduce reverb/echo buffer sizes
4. Enable low-latency audio mode

### For Better Quality
1. Use higher sample rates (if device supports)
2. Enable all enhancement features
3. Use larger buffer sizes
4. Fine-tune EQ for your voice

## Testing

### Unit Tests
```bash
./gradlew test
```

### Integration Tests
```bash
./gradlew connectedAndroidTest
```

### Manual Testing Checklist
- [ ] Voice enhancer reduces background noise
- [ ] Equalizer presets sound correct
- [ ] Voice changer effects work as expected
- [ ] Audio effects apply correctly
- [ ] Settings save and load properly
- [ ] No audio glitches or artifacts
- [ ] Latency is acceptable
- [ ] CPU usage is reasonable
- [ ] Works with Discord voice chat

## Troubleshooting

### No Audio Output
- Check microphone permissions
- Verify audio session ID is valid
- Ensure voice enhancement is enabled
- Check Discord voice connection status

### Audio Artifacts/Glitches
- Reduce number of active effects
- Increase buffer size
- Disable voice changer if using older device
- Check CPU usage

### High Latency
- Disable unused effects
- Use simpler processing
- Enable low-latency mode in Android settings
- Reduce buffer sizes

### High CPU Usage
- Disable complex effects (reverb, pitch shift)
- Use native equalizer only
- Profile code to find bottlenecks
- Consider background thread processing

## Future Improvements

### Phase Vocoder for Pitch Shifting
Replace simple resampling with phase vocoder for better quality:
- Preserves audio formants
- Less artifacts
- Better quality at extreme pitch shifts

### Hardware Acceleration
Utilize Android's audio HAL for processing:
- Lower latency
- Better battery life
- Higher quality

### Advanced Noise Reduction
Implement spectral subtraction or neural network-based noise reduction:
- Better noise suppression
- Preserves voice quality
- Adaptive to environment

### Spatial Audio
Add 3D audio positioning:
- Immersive experience
- Better directional audio
- Enhanced gaming experience

## API Reference

### VoiceEnhancementManager

```kotlin
class VoiceEnhancementManager(audioSessionId: Int) {
    fun processAudio(input: ShortArray): ShortArray
    fun setEnabled(enabled: Boolean)
    fun release()
}
```

### VoiceEnhancerPlugin

```kotlin
class VoiceEnhancerPlugin {
    var gain: Float
    var noiseThreshold: Float
    fun setGainDb(db: Float)
    fun process(input: FloatArray): FloatArray
}
```

### EqualizerPlugin

```kotlin
class EqualizerPlugin(audioSessionId: Int) {
    fun setBandGain(band: Int, gainDb: Float)
    fun applyPreset(name: String)
    fun getBands(): FloatArray
    fun reset()
}
```

### VoiceChangerPlugin

```kotlin
class VoiceChangerPlugin {
    var pitchShift: Float
    var formantShift: Float
    fun applyEffect(effectName: String): Boolean
    fun process(input: FloatArray): FloatArray
}
```

### AudioEffectsPlugin

```kotlin
class AudioEffectsPlugin {
    var reverbEnabled: Boolean
    var echoEnabled: Boolean
    var chorusEnabled: Boolean
    var distortionEnabled: Boolean
    
    fun setReverbMix(mix: Float)
    fun setEchoTime(time: Float)
    fun process(input: FloatArray): FloatArray
}
```

## Contributing

When adding new features:

1. Follow existing code structure
2. Add unit tests
3. Document performance impact
4. Update this guide
5. Test on multiple devices

## License

Same as Bluecord project.
