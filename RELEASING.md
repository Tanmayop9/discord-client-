# Creating Releases

This document explains how to create and publish releases with APK files for users to download.

## Automated Release Process

This repository uses GitHub Actions to automatically build and publish APK files when you create a new release tag.

### Quick Release Steps

1. **Update version in code** (optional but recommended):
   ```bash
   # Edit app/build.gradle
   # Update versionCode and versionName
   ```

2. **Create and push a tag**:
   ```bash
   git tag -a v1.0.0 -m "Release version 1.0.0"
   git push origin v1.0.0
   ```

3. **GitHub Actions automatically**:
   - Builds both release and debug APKs
   - Creates a GitHub Release
   - Uploads APKs to the release
   - Adds release notes template

4. **Edit the release** (optional):
   - Go to the [Releases page](../../releases)
   - Click "Edit" on the new release
   - Add detailed release notes
   - Update changelog

### Version Numbering

Follow [Semantic Versioning](https://semver.org/):
- **v1.0.0** - Major release (breaking changes)
- **v1.1.0** - Minor release (new features)
- **v1.1.1** - Patch release (bug fixes)

### Example: Creating v1.0.0

```bash
# 1. Update version in app/build.gradle
sed -i 's/versionCode .*/versionCode 1/' app/build.gradle
sed -i 's/versionName .*/versionName "1.0.0"/' app/build.gradle

# 2. Commit version bump
git add app/build.gradle
git commit -m "Bump version to 1.0.0"
git push

# 3. Create and push tag
git tag -a v1.0.0 -m "Release version 1.0.0 - Initial public release"
git push origin v1.0.0

# 4. Wait for GitHub Actions to complete (~5-10 minutes)
# 5. Check the Releases page for your new release with APKs
```

### Release Workflow Details

The release workflow (`.github/workflows/release.yml`):

1. **Triggers** on tags matching `v*` pattern
2. **Builds** both release and debug APKs
3. **Creates** a GitHub Release automatically
4. **Uploads** APK files to the release
5. **Adds** installation instructions

### Build Workflow

The build workflow (`.github/workflows/build.yml`):

- Runs on every push to main/master/develop branches
- Runs on every pull request
- Builds debug APK to verify compilation
- Uploads artifacts for testing (retained for 30 days)

### Manual Release (Alternative)

If you need to create a release manually:

1. **Build locally**:
   ```bash
   ./gradlew assembleRelease
   ./gradlew assembleDebug
   ```

2. **Create tag**:
   ```bash
   git tag -a v1.0.0 -m "Release 1.0.0"
   git push origin v1.0.0
   ```

3. **Create release on GitHub**:
   - Go to repository → **Releases** → **Create a new release**
   - Select the tag you just created
   - Add release title and description
   - Upload APK files from:
     - `app/build/outputs/apk/release/app-release.apk`
     - `app/build/outputs/apk/debug/app-debug.apk`
   - Click **Publish release**

### Pre-release vs Release

**Pre-release** (beta testing):
```bash
git tag -a v1.0.0-beta.1 -m "Beta release 1.0.0-beta.1"
git push origin v1.0.0-beta.1
```
Then mark the release as "pre-release" on GitHub.

**Full release**:
```bash
git tag -a v1.0.0 -m "Release 1.0.0"
git push origin v1.0.0
```

### Updating Existing Release

If you need to update a release:

1. **Delete the tag** (locally and remotely):
   ```bash
   git tag -d v1.0.0
   git push origin :refs/tags/v1.0.0
   ```

2. **Delete the release** on GitHub (go to Releases page)

3. **Recreate** the tag and release following the steps above

### Troubleshooting

**Build fails in GitHub Actions**:
- Check the Actions tab for error logs
- Ensure the code builds locally first: `./gradlew assembleRelease`
- Fix any issues and create a new tag

**APK not uploaded**:
- Check workflow permissions (ensure `contents: write` is set)
- Verify GITHUB_TOKEN has sufficient permissions

**Tag already exists**:
```bash
# Delete remote tag
git push origin :refs/tags/v1.0.0

# Delete local tag
git tag -d v1.0.0

# Create new tag
git tag -a v1.0.0 -m "Your message"
git push origin v1.0.0
```

## Release Checklist

Before creating a release:

- [ ] Code is tested and working
- [ ] Version number updated in `app/build.gradle`
- [ ] All changes merged to main/master branch
- [ ] CHANGELOG updated (if exists)
- [ ] Tag name follows semantic versioning (v1.0.0)
- [ ] Release notes prepared

After creating a release:

- [ ] GitHub Actions workflow completed successfully
- [ ] APK files are attached to the release
- [ ] Release notes are clear and informative
- [ ] Download and test the APK on a device
- [ ] Announce the release (if applicable)

## Testing APKs

Before announcing a release, test the APKs:

1. **Download** from the release page
2. **Install** on a physical device or emulator
3. **Verify** all features work correctly
4. **Check** app version matches release version

```bash
# Install via ADB
adb install app-release.apk

# Check installed version
adb shell dumpsys package com.bluecord | grep versionName
```

## Best Practices

1. **Test before tagging** - Ensure code works before creating a tag
2. **Write clear release notes** - Users appreciate knowing what changed
3. **Use semantic versioning** - Makes version numbers meaningful
4. **Keep tags immutable** - Don't reuse or change tags after pushing
5. **Regular releases** - Don't accumulate too many changes between releases

## Support

For issues with the release process:
- Check GitHub Actions logs
- Review this documentation
- Open an issue with the `release` label
