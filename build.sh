#!/bin/bash

# IEM1Chr Build Script
# Copy & paste to build and test the launcher

echo "🚀 IEM1Chr Build Script"
echo "======================"

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${YELLOW}[1/4] Cleaning build...${NC}"
./gradlew clean

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Clean failed${NC}"
    exit 1
fi

echo -e "${YELLOW}[2/4] Building debug APK...${NC}"
./gradlew assembleDebug

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Build failed${NC}"
    exit 1
fi

echo -e "${YELLOW}[3/4] Installing on device...${NC}"
./gradlew installDebug

if [ $? -ne 0 ]; then
    echo -e "${RED}❌ Install failed - is device connected?${NC}"
    echo "Connect device and enable USB debugging"
    echo "Then run: adb devices"
    exit 1
fi

echo -e "${YELLOW}[4/4] Launching app...${NC}"
adb shell am start -n com.jeropmelanie.iem1chr/com.jeropmelanie.iem1chr.ui.MainActivity

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ Build & launch successful!${NC}"
    echo ""
    echo "Next steps:"
    echo "1. Try swiping left/right/up/down"
    echo "2. Double tap the screen"
    echo "3. Long press the screen"
    echo ""
    echo "View logs: adb logcat | grep iem1chr"
else
    echo -e "${RED}❌ Launch failed${NC}"
fi
