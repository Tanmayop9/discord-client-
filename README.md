# Enhanced Bluecord - Discord Client with Voice Enhancements

A fork of [Bluecord](https://github.com/bluemods/Bluecord) with advanced voice chat features including voice changer, audio equalizer, noise reduction, and professional audio effects.

> **📦 Quick Install**: Download the latest APK from the [Releases page](../../releases) - no building required!

## ✨ New Features

### 🎙️ Voice Enhancement
- **Advanced Noise Reduction**: Eliminates background noise with adjustable noise gate
- **Auto Gain Control**: Automatic volume normalization for consistent audio levels
- **Voice Clarity Enhancement**: Optimized processing for crystal-clear voice communication

### 🎚️ Professional Equalizer
- Android native Equalizer integration with custom presets:
  - **Flat**: Neutral sound (default)
  - **Bass Boost**: Enhanced low frequencies
  - **Treble Boost**: Enhanced high frequencies  
  - **Voice Clarity**: Optimized for clear speech
  - **Deep Voice**: Lower, more authoritative voice
  - **Bright Voice**: Higher, more energetic voice
  - **Radio**: Classic radio transmission sound
  - **Podcast**: Professional broadcast quality

### 🎭 Real-Time Voice Changer
Multiple voice effect presets:
- **Normal**: No voice modification
- **Robot**: Mechanical/robotic voice
- **Chipmunk**: High-pitched squeaky voice
- **Deep Voice**: Lower bass-heavy voice
- **Demon**: Dark, demonic effect
- **Radio**: AM radio transmission
- **Telephone**: Phone call quality
- **Cave Echo**: Reverberant cave effect

Custom controls:
- Pitch shift: -12 to +12 semitones
- Formant shift: Adjust voice character

### 🎵 Audio Effects
- **Reverb**: Add room ambience and space
- **Echo**: Delay-based echo effect
- **Chorus**: Rich, doubled voice effect
- **Distortion**: Creative overdrive effect

## 🚀 Building from Source

### Prerequisites
- Android SDK (API 21+)
- JDK 8 or higher
- Gradle 7.0+

### Build Steps

1. **Clone the repository**
```bash
git clone https://github.com/Tanmayop9/discord-client-.git
cd discord-client-
```

2. **Build using Gradle**
```bash
chmod +x gradlew
./gradlew assembleDebug
```

3. **Install the APK**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## 📱 Installation

### From APK (Recommended - No Build Required!)

**Easy installation in 3 steps:**

1. **Download the APK**
   - Go to the [Releases page](../../releases)
   - Download the latest `app-release.apk` file
   - (Alternatively, download `app-debug.apk` for a debug build)

2. **Enable Unknown Sources**
   - Go to **Settings** → **Security** → **Unknown Sources** (enable)
   - Or **Settings** → **Apps** → **Special Access** → **Install Unknown Apps** → Select your browser/file manager → **Allow**

3. **Install**
   - Open the downloaded APK file
   - Tap **Install**
   - Grant microphone and storage permissions when prompted
   - Done! Launch the app and enjoy

### From Source
Follow the "Building from Source" steps above.

## 🎛️ Usage

### Accessing Voice Enhancement Settings

1. Open the app
2. Go to **Settings** → **Voice Enhancement**
3. Configure your preferences:

#### Voice Enhancer
- Enable/disable voice enhancement
- Adjust voice gain (volume boost)
- Set noise gate threshold

#### Equalizer
- Enable/disable EQ
- Select a preset or customize bands
- Fine-tune frequency response

#### Voice Changer
- Enable/disable voice changing
- Choose an effect preset
- Manually adjust pitch and formants

#### Audio Effects
- Enable individual effects (reverb, echo, chorus, distortion)
- Adjust effect parameters (mix, time, feedback)

### Quick Start Guide

**For Clearer Voice:**
1. Enable Voice Enhancement
2. Set noise threshold to -50dB
3. Apply "Voice Clarity" EQ preset

**For Streaming/Podcasting:**
1. Enable Voice Enhancement with gain at 120%
2. Apply "Podcast" EQ preset
3. Add slight reverb (20-30% mix)

**For Anonymous Voice:**
1. Enable Voice Changer
2. Set pitch to -7 semitones
3. Add reverb and chorus effects

## 🔧 Technical Details

### Audio Processing Pipeline
```
Input → Voice Enhancer → Equalizer → Voice Changer → Audio Effects → Output
```

### Sample Rate
- 48kHz (standard for Discord)
- Low-latency processing

### Compatibility
- Android 5.0 (API 21) and above
- Optimized for modern devices
- Works with all Discord voice features

## 📋 Permissions

Required permissions:
- `RECORD_AUDIO`: For voice input
- `MODIFY_AUDIO_SETTINGS`: For audio effects
- `INTERNET`: For Discord connectivity
- `ACCESS_NETWORK_STATE`: Network status

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### For Maintainers

To create a new release with APK files:

```bash
# Update version in app/build.gradle, then:
git tag -a v1.0.0 -m "Release version 1.0.0"
git push origin v1.0.0
```

GitHub Actions will automatically build and publish the APK to the [Releases page](../../releases).

See [RELEASING.md](RELEASING.md) for detailed release instructions.

## 📄 License

This project inherits the license from [Bluecord](https://github.com/bluemods/Bluecord).

See `LICENSE.md` for details.

## 🙏 Credits

- **Bluecord Team**: Original Discord client modification
- **Aliucord**: Plugin architecture inspiration
- **Vencord**: Feature ideas and concepts

### Original Bluecord Features

All original Bluecord features are preserved:
- Anti-Delete: Block message deletions
- Anti-Edit: Track message edits
- Custom themes
- Emote enhancements
- And many more!

See the [original Bluecord README](https://github.com/bluemods/Bluecord) for full feature list.

## ⚠️ Disclaimer

This is an **unofficial** Discord client modification. Use at your own risk.

- Modifying Discord clients may violate Discord's Terms of Service
- We are not responsible for any account actions
- This is for educational purposes
- No warranty or guarantee is provided

## 🐛 Known Issues

- Voice changing may introduce slight latency on older devices
- Some effects require device-specific tuning
- Battery usage may increase with multiple effects enabled

## 🔮 Planned Features

- [ ] More voice effect presets
- [ ] Custom EQ band configuration
- [ ] Audio visualizer
- [ ] Voice recording with effects
- [ ] Effect presets import/export
- [ ] Per-server effect profiles
- [ ] Advanced pitch correction
- [ ] Spatial audio effects

## 📞 Support

For issues and questions:
- Open an [Issue](../../issues)
- Check existing issues first
- Provide device info and logs when reporting bugs

## 🌟 Star History

If you find this useful, please star the repository!

---

**Built with ❤️ for the Discord community**
