# Usage Guide - Voice Enhancement Features

This guide provides practical examples for using the voice enhancement features in Enhanced Bluecord.

## Quick Setup

### First Time Setup

1. **Install the APK**
   - Download from releases or build from source
   - Install on your Android device (API 21+)
   - Grant microphone and storage permissions

2. **Open Settings**
   - Launch Enhanced Bluecord
   - Navigate to **Settings**
   - Find **Voice Enhancement** section

3. **Test Your Voice**
   - Join a Discord voice channel
   - Speak into your microphone
   - Adjust settings as needed

## Common Use Cases

### 1. Clear Voice Communication

**Goal**: Crystal clear voice for professional calls or streaming

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 120%
  Noise Threshold: -50dB

Equalizer:
  ✓ Enabled
  Preset: Voice Clarity

Voice Changer:
  ✗ Disabled

Audio Effects:
  ✗ Disabled
```

**Result**: Professional-quality voice with background noise eliminated

---

### 2. Gaming Voice Chat

**Goal**: Reduce keyboard/mouse noise while keeping voice clear

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 110%
  Noise Threshold: -40dB (aggressive)

Equalizer:
  ✓ Enabled
  Preset: Flat or Bright Voice

Voice Changer:
  ✗ Disabled

Audio Effects:
  ✗ Disabled
```

**Result**: Mechanical keyboard sounds suppressed, voice comes through clearly

---

### 3. Podcast/Streaming Setup

**Goal**: Professional broadcast-quality audio

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 130%
  Noise Threshold: -55dB

Equalizer:
  ✓ Enabled
  Preset: Podcast

Voice Changer:
  ✗ Disabled

Audio Effects:
  ✓ Enabled
  Reverb: ✓ (Mix: 15%)
  Echo: ✗
  Chorus: ✗
  Distortion: ✗
```

**Result**: Rich, professional sound with subtle room presence

---

### 4. Anonymous Voice

**Goal**: Disguise your voice for privacy

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 100%
  Noise Threshold: -50dB

Equalizer:
  ✓ Enabled
  Preset: Deep Voice

Voice Changer:
  ✓ Enabled
  Effect: Deep Voice
  Pitch: -7 semitones
  Formant: -5

Audio Effects:
  ✓ Enabled
  Reverb: ✓ (Mix: 20%)
  Chorus: ✓
```

**Result**: Unrecognizable, deep altered voice

---

### 5. Fun Voice Effects

**Goal**: Entertainment and roleplay

**Settings for Different Characters**:

**Robot Character**:
```
Voice Changer:
  ✓ Enabled
  Effect: Robot
  
Audio Effects:
  ✓ Enabled
  Distortion: ✓
```

**Chipmunk/Cartoon**:
```
Voice Changer:
  ✓ Enabled
  Effect: Chipmunk (High pitch)
  
Equalizer:
  Preset: Bright Voice
```

**Demon/Villain**:
```
Voice Changer:
  ✓ Enabled
  Effect: Demon (-8 semitones)
  
Audio Effects:
  Reverb: ✓ (Mix: 40%)
  Distortion: ✓
  
Equalizer:
  Preset: Bass Boost
```

---

### 6. Music Producer Voice

**Goal**: Rich, radio-quality voice for music discussions

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 140%
  Noise Threshold: -60dB

Equalizer:
  ✓ Enabled
  Preset: Radio

Voice Changer:
  ✗ Disabled

Audio Effects:
  ✓ Enabled
  Reverb: ✓ (Mix: 10%)
  Chorus: ✓
```

**Result**: Warm, radio-style voice perfect for discussing music

---

### 7. ASMR/Soft Speaking

**Goal**: Capture quiet, intimate voice clearly

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 180% (high gain for quiet voice)
  Noise Threshold: -65dB (sensitive)

Equalizer:
  ✓ Enabled
  Preset: Voice Clarity

Audio Effects:
  ✓ Enabled
  Reverb: ✓ (Mix: 25%)
```

**Result**: Soft whispers captured with clarity and depth

---

### 8. Noisy Environment

**Goal**: Voice chat from noisy location (cafe, street, etc.)

**Settings**:
```
Voice Enhancement:
  ✓ Enabled
  Gain: 100%
  Noise Threshold: -35dB (very aggressive)

Equalizer:
  ✓ Enabled
  Preset: Voice Clarity

Voice Changer:
  ✗ Disabled

Audio Effects:
  ✗ Disabled
```

**Result**: Background noise heavily suppressed, voice prioritized

---

## Settings Reference

### Voice Enhancement

#### Gain
- **60-80%**: Quieter, more natural
- **90-110%**: Normal voice level
- **120-150%**: Boosted for quiet mics
- **160-200%**: Maximum amplification (may clip)

#### Noise Threshold
- **-30 to -40dB**: Very aggressive (blocks most noise)
- **-45 to -55dB**: Balanced (recommended)
- **-60 to -75dB**: Gentle (preserves more ambient sound)
- **-80 to -100dB**: Minimal filtering

### Equalizer Presets

| Preset | Best For | Description |
|--------|----------|-------------|
| Flat | Reference | No modification |
| Bass Boost | Deep voice | Enhanced low end |
| Treble Boost | Clarity | Enhanced high end |
| Voice Clarity | Communication | Optimized for speech |
| Deep Voice | Authority | Lower, deeper tone |
| Bright Voice | Energy | Higher, brighter tone |
| Radio | Broadcasting | Classic radio sound |
| Podcast | Professional | Broadcast quality |

### Voice Effects

| Effect | Pitch Shift | Formant Shift | Use Case |
|--------|-------------|---------------|----------|
| Normal | 0 | 0 | No change |
| Robot | -2 | -3 | Mechanical character |
| Chipmunk | +7 | +5 | Cartoon character |
| Deep Voice | -5 | -4 | Authority figure |
| Demon | -8 | -6 | Villain/monster |
| Radio | 0 | -1 | AM radio style |
| Telephone | +1 | +2 | Phone quality |
| Cave Echo | -1 | 0 | Echoey environment |

### Audio Effects

#### Reverb
- **Mix 10-20%**: Subtle room presence
- **Mix 25-40%**: Noticeable space
- **Mix 50%+**: Cathedral/large hall

#### Echo
- **Time 0.1-0.2s**: Tight slapback
- **Time 0.3-0.5s**: Standard delay
- **Time 0.6-1.0s**: Long echo

- **Feedback 0-30%**: Single repeat
- **Feedback 40-60%**: Multiple repeats
- **Feedback 70%+**: Infinite decay

## Troubleshooting

### Audio Sounds Robotic/Choppy
**Solution**:
- Reduce number of active effects
- Disable voice changer
- Lower pitch shift amount
- Check CPU usage

### Voice is Too Quiet
**Solution**:
- Increase Voice Enhancer Gain
- Apply Bass Boost or Voice Clarity EQ
- Check Android audio levels
- Verify microphone isn't blocked

### Too Much Background Noise
**Solution**:
- Lower Noise Threshold (more negative)
- Disable audio effects
- Use Voice Clarity EQ preset
- Check microphone positioning

### Voice Sounds Unnatural
**Solution**:
- Reduce Gain below 120%
- Use Flat EQ preset
- Disable Voice Changer
- Reduce effect mix amounts

### High Battery Drain
**Solution**:
- Disable unused effects
- Use Flat EQ (least processing)
- Disable Voice Changer
- Lower voice quality in Discord settings

### Choppy Audio/Lag
**Solution**:
- Close background apps
- Disable complex effects (reverb, voice changer)
- Use simpler EQ presets
- Check device temperature

## Tips and Tricks

### Save Your Settings
Settings are automatically saved in Android SharedPreferences. Your custom configuration persists across app restarts.

### Per-Server Profiles
Currently not supported, but you can note your preferred settings for different servers and manually switch.

### Keyboard Shortcuts
Not available in Android version. Use quick settings toggles instead.

### Testing Your Settings
1. Join a private voice channel alone
2. Start screen recording with audio
3. Speak and test different settings
4. Review the recording
5. Fine-tune settings

### Combining Effects
Best combinations:
- **Professional**: Voice Enhancer + Podcast EQ + Light Reverb (10%)
- **Gaming**: Voice Enhancer + Voice Clarity EQ
- **Anonymous**: Deep Voice Effect + Reverb + Chorus
- **Character**: Voice Effect + Matching EQ + Effects

### Performance Optimization
Enable only what you need:
- Basic: Voice Enhancer only (lowest CPU)
- Balanced: Voice Enhancer + EQ (medium CPU)
- Full: All features (highest CPU, best quality)

## Advanced Usage

### Custom Voice Effects
Manually adjust pitch and formant for unique voices:

**Male to Female**:
- Pitch: +4 to +6
- Formant: +3 to +5

**Female to Male**:
- Pitch: -4 to -6
- Formant: -3 to -5

**Monster Voice**:
- Pitch: -10 to -12
- Formant: -8 to -10
- Add distortion

### Creative Sound Design
Layer multiple effects for unique results:
1. Start with Voice Changer effect
2. Add matching EQ preset
3. Layer reverb for space
4. Add echo for dimension
5. Fine-tune mix levels

## FAQ

**Q: Will this work with Discord's built-in noise suppression?**
A: Yes, but you may want to disable Discord's noise suppression to avoid double processing.

**Q: Can I use this while screen recording?**
A: Yes, the enhanced audio is sent through Discord and will be captured in screen recordings.

**Q: Does this work in video calls?**
A: Yes, works with both voice and video calls.

**Q: Can others hear my voice enhancements?**
A: Yes, all processing is done before sending to Discord, so others hear your enhanced voice.

**Q: Will this ban my Discord account?**
A: This modifies the client app, which may violate Discord ToS. Use at your own risk.

**Q: Can I export/import settings?**
A: Not currently implemented, but planned for future versions.

## Support

If you encounter issues:
1. Check this guide first
2. Review the troubleshooting section
3. Check GitHub issues
4. Create a new issue with:
   - Device model
   - Android version
   - Settings used
   - Description of problem

---

**Enjoy your enhanced voice chat experience!** 🎙️
