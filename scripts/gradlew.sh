#!/bin/sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# SPDX-License-Identifier: Apache-2.0
#

##############################################################################
#
#  Gradle startup script for Linux (Debian, Ubuntu, Fedora, Arch, openSUSE)
#  Based on gradlew.bat
#
##############################################################################

# Enable debug mode if DEBUG variable is set
if [ -n "$DEBUG" ]; then
    set -x
fi

# Resolve symlinks to find the real application home directory
PRG="$0"
while [ -h "$PRG" ]; do
    ls=$(ls -ld "$PRG")
    link=$(expr "$ls" : '^.*-> \(.*\)$')
    if expr "$link" : '^/' > /dev/null; then
        PRG="$link"
    else
        PRG="$(dirname "$PRG")/$link"
    fi
done

DIRNAME=$(dirname "$PRG")
APP_HOME=$(cd -P "$DIRNAME" && pwd)
APP_BASE_NAME=$(basename "$PRG")

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Find java executable
if [ -n "$JAVA_HOME" ]; then
    JAVA_EXE="$JAVA_HOME/bin/java"
    if [ ! -x "$JAVA_EXE" ]; then
        echo "" >&2
        echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
        echo "" >&2
        echo "Please set the JAVA_HOME variable in your environment to match the" >&2
        echo "location of your Java installation." >&2
        exit 1
    fi
elif command -v java >/dev/null 2>&1; then
    JAVA_EXE="java"
else
    # Auto-detect Java across standard Linux distributions
    # (Debian, Ubuntu, Fedora, Arch Linux, openSUSE)
    for cand in \
        /usr/lib/jvm/default-java/bin/java \
        /usr/lib/jvm/default-runtime/bin/java \
        /usr/lib/jvm/default/bin/java \
        /usr/lib64/jvm/default-runtime/bin/java \
        /usr/lib64/jvm/default/bin/java \
        /usr/lib64/jvm/java/bin/java \
        /usr/lib/jvm/java/bin/java \
        /usr/lib/jvm/java-openjdk/bin/java \
        /usr/lib/jvm/jre-openjdk/bin/java \
        /usr/lib64/jvm/jre/bin/java \
        /usr/lib/jvm/java-*-openjdk*/bin/java \
        /usr/lib64/jvm/java-*-openjdk*/bin/java; do
        if [ -x "$cand" ]; then
            JAVA_EXE="$cand"
            export JAVA_HOME="$(dirname "$(dirname "$cand")")"
            break
        fi
    done

    if [ -z "$JAVA_EXE" ] || [ ! -x "$JAVA_EXE" ]; then
        echo "" >&2
        echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >&2
        echo "" >&2
        echo "Please set the JAVA_HOME variable in your environment to match the" >&2
        echo "location of your Java installation, or install Java via your package manager:" >&2
        echo "  - Debian / Ubuntu:  sudo apt install default-jdk" >&2
        echo "  - Fedora:           sudo dnf install java-latest-openjdk-devel" >&2
        echo "  - Arch Linux:       sudo pacman -S jdk-openjdk" >&2
        echo "  - openSUSE:         sudo zypper install java-21-openjdk-devel" >&2
        echo "" >&2
        exit 1
    fi
fi

# Verify Gradle wrapper JAR existence
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ ! -r "$WRAPPER_JAR" ]; then
    echo "" >&2
    echo "ERROR: Gradle wrapper JAR not found: $WRAPPER_JAR" >&2
    echo "Please make sure gradle/wrapper/gradle-wrapper.jar is present in your project." >&2
    echo "" >&2
    exit 1
fi

# Setup classpath
CLASSPATH=""

# Setup Gradle execution arguments
set -- \
    "-Dorg.gradle.appname=$APP_BASE_NAME" \
    -classpath "$CLASSPATH" \
    -jar "$WRAPPER_JAR" \
    "$@"

# Parse JVM options (preserving quotes and handling empty variables)
if command -v xargs >/dev/null 2>&1; then
    eval "set -- $(
        printf '%s\n' "$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS" |
        xargs -n1 |
        sed ' s~[^-[:alnum:]+,./:=@_]~\\&~g; ' |
        tr '\n' ' '
    )" '"$@"'
else
    eval "set -- $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS" '"$@"'
fi

# Execute Gradle
exec "$JAVA_EXE" "$@"
