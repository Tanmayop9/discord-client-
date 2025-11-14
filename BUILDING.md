# Building Enhanced Bluecord

Complete guide for building Enhanced Bluecord APK from source.

## Prerequisites

### Required Software

1. **Java Development Kit (JDK)**
   - JDK 8 or higher
   - JDK 11 recommended
   - Download: https://adoptium.net/

2. **Android SDK**
   - Android SDK Platform 34
   - Build Tools 30.0.0+
   - Platform Tools

3. **Git**
   - For cloning the repository
   - Download: https://git-scm.com/

### Optional but Recommended

- **Android Studio** - For easier development and debugging
- **Gradle** - Usually bundled with project (gradlew)

## Quick Build (Using Gradle Wrapper)

### 1. Clone Repository

```bash
git clone https://github.com/Tanmayop9/discord-client-.git
cd discord-client-
```

### 2. Set Execute Permissions (Linux/Mac)

```bash
chmod +x gradlew
```

### 3. Build Debug APK

```bash
./gradlew assembleDebug
```

**Windows**:
```cmd
gradlew.bat assembleDebug
```

### 4. Locate APK

Built APK will be at:
```
app/build/outputs/apk/debug/app-debug.apk
```

### 5. Install on Device

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Detailed Build Steps

### Environment Setup

#### Linux/Mac

1. **Install JDK**:
```bash
# Ubuntu/Debian
sudo apt install openjdk-11-jdk

# macOS (using Homebrew)
brew install openjdk@11
```

2. **Set JAVA_HOME**:
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

Add to `~/.bashrc` or `~/.zshrc` for persistence.

3. **Install Android SDK** (if not using Android Studio):
```bash
# Download command line tools
wget https://dl.google.com/android/repository/commandlinetools-linux-latest.zip
unzip commandlinetools-linux-latest.zip -d ~/android-sdk

# Set ANDROID_HOME
export ANDROID_HOME=~/android-sdk
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH

# Install SDK packages
sdkmanager "platforms;android-34"
sdkmanager "build-tools;30.0.0"
sdkmanager "platform-tools"
```

#### Windows

1. **Install JDK**:
   - Download from https://adoptium.net/
   - Run installer
   - Set JAVA_HOME in System Environment Variables

2. **Install Android SDK**:
   - Download Android Studio or Command Line Tools
   - Set ANDROID_HOME environment variable
   - Add to PATH: `%ANDROID_HOME%\platform-tools`

3. **Verify Installation**:
```cmd
java -version
adb version
```

### Build Configurations

#### Debug Build (Development)

```bash
./gradlew assembleDebug
```

Features:
- No code obfuscation
- Includes debugging symbols
- Larger APK size
- Easier to debug

#### Release Build (Production)

```bash
./gradlew assembleRelease
```

Features:
- Code minification
- ProGuard/R8 optimization
- Smaller APK size
- Requires signing

**Note**: Release builds need a signing key (see Signing section).

### Gradle Tasks

View all available tasks:
```bash
./gradlew tasks
```

Common tasks:
```bash
./gradlew clean            # Clean build artifacts
./gradlew build            # Build all variants
./gradlew assembleDebug    # Build debug APK
./gradlew assembleRelease  # Build release APK
./gradlew installDebug     # Build and install debug
./gradlew test             # Run tests
./gradlew lint             # Run linter
```

## Signing the APK

### Generate Signing Key

```bash
keytool -genkey -v -keystore my-release-key.jks \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias my-key-alias
```

Follow prompts to set:
- Password for keystore
- Name and organization details
- Password for key

### Configure Signing

Create `app/signing.properties`:
```properties
storeFile=../my-release-key.jks
storePassword=your_keystore_password
keyAlias=my-key-alias
keyPassword=your_key_password
```

**Important**: Add `signing.properties` to `.gitignore` - never commit passwords!

Update `app/build.gradle`:
```gradle
android {
    signingConfigs {
        release {
            def props = new Properties()
            file("signing.properties").withInputStream { props.load(it) }
            storeFile file(props['storeFile'])
            storePassword props['storePassword']
            keyAlias props['keyAlias']
            keyPassword props['keyPassword']
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
        }
    }
}
```

Build signed release:
```bash
./gradlew assembleRelease
```

## Troubleshooting

### Common Build Errors

#### "SDK location not found"

**Solution**: Create `local.properties`:
```properties
sdk.dir=/path/to/android/sdk
```

#### "Execution failed for task ':app:processDebugResources'"

**Solution**:
```bash
./gradlew clean
./gradlew assembleDebug
```

#### "Could not resolve dependencies"

**Solution**:
```bash
# Check internet connection
# Try with VPN if behind firewall

# Clear Gradle cache
rm -rf ~/.gradle/caches/
./gradlew assembleDebug --refresh-dependencies
```

#### "Unable to start daemon process"

**Solution**:
```bash
# Increase Gradle memory in gradle.properties
org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=512m
```

#### "Unsupported class file major version"

**Solution**: Your JDK is too new or too old
```bash
# Check Java version
java -version

# Should be 8-17 for this project
# Install appropriate version and set JAVA_HOME
```

### Build Performance

#### Speed up builds:

1. **Enable Gradle Daemon** (in `gradle.properties`):
```properties
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.configureondemand=true
```

2. **Increase Heap Size**:
```properties
org.gradle.jvmargs=-Xmx4096m
```

3. **Use Build Cache**:
```properties
org.gradle.caching=true
```

4. **Offline Mode** (after first build):
```bash
./gradlew assembleDebug --offline
```

## Using Android Studio

### Import Project

1. Open Android Studio
2. **File** → **Open**
3. Select `discord-client-` directory
4. Wait for Gradle sync
5. Click **Build** → **Build Bundle(s) / APK(s)** → **Build APK(s)**

### Run on Device

1. Connect Android device via USB
2. Enable USB Debugging in Developer Options
3. Click Run button (▶️) in Android Studio
4. Select your device
5. App will build and install automatically

## Custom Modifications

### Change App Name

Edit `patch/res/values/strings.xml`:
```xml
<string name="app_name">Your App Name</string>
```

### Change Package Name

1. Update `app/build.gradle`:
```gradle
android {
    defaultConfig {
        applicationId "com.yourname.discord"
    }
}
```

2. Update `AndroidManifest.xml` package reference

3. Rename package directories in `app/src/main/java/`

### Change App Icon

Replace files in:
- `patch/res/mipmap-hdpi/`
- `patch/res/mipmap-mdpi/`
- `patch/res/mipmap-xhdpi/`
- `patch/res/mipmap-xxhdpi/`
- `patch/res/mipmap-xxxhdpi/`

## Build Variants

### Product Flavors (if configured)

```bash
./gradlew assembleFreeDebug      # Free version, debug
./gradlew assembleFreeRelease    # Free version, release
./gradlew assembleProDebug       # Pro version, debug
./gradlew assembleProRelease     # Pro version, release
```

## Continuous Integration

### GitHub Actions Example

Create `.github/workflows/build.yml`:
```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'adopt'
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build with Gradle
      run: ./gradlew assembleDebug
    
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
```

## Distribution

### GitHub Releases

1. **Tag version**:
```bash
git tag -a v1.0.0 -m "Version 1.0.0"
git push origin v1.0.0
```

2. **Create release on GitHub**:
   - Go to repository → Releases → New Release
   - Select tag
   - Upload APK
   - Add release notes

### F-Droid Preparation

F-Droid requires:
- Open source dependencies only
- Reproducible builds
- No proprietary SDKs

### Direct Distribution

Requirements:
- Users must enable "Install from Unknown Sources"
- Provide SHA256 checksum for verification

Generate checksum:
```bash
sha256sum app/build/outputs/apk/release/app-release.apk
```

## Build from Specific Commit

```bash
git clone https://github.com/Tanmayop9/discord-client-.git
cd discord-client-
git checkout <commit-hash>
./gradlew assembleDebug
```

## Build Size Optimization

### Reduce APK Size

1. **Enable ProGuard/R8**:
```gradle
buildTypes {
    release {
        minifyEnabled true
        shrinkResources true
    }
}
```

2. **Use APK Splits**:
```gradle
android {
    splits {
        abi {
            enable true
            reset()
            include 'armeabi-v7a', 'arm64-v8a'
        }
    }
}
```

3. **Remove unused resources**:
```gradle
android {
    buildTypes {
        release {
            shrinkResources true
        }
    }
}
```

## Verification

### Verify Build

Check APK contents:
```bash
unzip -l app/build/outputs/apk/debug/app-debug.apk
```

### Test Installation

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Run on Emulator

```bash
# Create AVD
avdmanager create avd -n test_device -k "system-images;android-30;google_apis;x86_64"

# Start emulator
emulator -avd test_device

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Getting Help

If you encounter build issues:

1. Check this guide first
2. Review error messages carefully
3. Clean and rebuild: `./gradlew clean build`
4. Check [GitHub Issues](../../issues)
5. Provide full error log when asking for help

## Resources

- [Android Developer Guide](https://developer.android.com/)
- [Gradle Documentation](https://docs.gradle.org/)
- [ProGuard Manual](https://www.guardsquare.com/proguard/manual)
- [Bluecord Original Repo](https://github.com/bluemods/Bluecord)

---

**Happy Building!** 🛠️
