#!/bin/bash

# Quick Release Builder
# Build release APK and create GitHub release

echo "📦 IEM1Chr Release Builder"
echo "==========================="

echo "Enter version (e.g., 1.1.0):"
read VERSION

echo "Enter changelog:"
read CHANGELOG

echo -e "\n🔨 Building release APK for v${VERSION}..."

# Update version in gradle
sed -i "" "s/versionName .*/versionName \"${VERSION}\"/g" app/build.gradle

# Build
./gradlew clean
./gradlew assembleRelease

if [ $? -eq 0 ]; then
    APK_PATH="app/build/outputs/apk/release/app-release.apk"
    echo -e "\n✅ APK created: $APK_PATH"
    echo ""
    echo "📝 To create GitHub release:"
    echo "1. Go to: https://github.com/jeropmelanie/iem1Chr-/releases/new"
    echo "2. Tag: v${VERSION}"
    echo "3. Title: IEM1Chr v${VERSION}"
    echo "4. Description: ${CHANGELOG}"
    echo "5. Upload: $APK_PATH"
    echo "6. Publish"
else
    echo "❌ Build failed"
    exit 1
fi
