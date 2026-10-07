#!/usr/bin/env bash

#
# Development Watch Script for Unipocket in Waydroid
# Automatically rebuilds and redeploys the app to Waydroid on code changes.
#

set -e

DIRNAME="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIRNAME"

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
PACKAGE_NAME="com.vanyookk.unipocket"

build_and_deploy() {
    echo ""
    echo "=================================================="
    echo "🔨 [$(date +'%H:%M:%S')] Пересборка проекта..."
    echo "=================================================="
    
    if ./gradlew.sh assembleDebug; then
        echo "📲 Установка APK в Waydroid..."
        if waydroid app install "$APK_PATH" 2>/dev/null; then
            echo "🚀 Перезапуск приложения..."
            waydroid app launch "$PACKAGE_NAME" >/dev/null 2>&1 || true
            echo "✨ Готово! Приложение обновлено в Waydroid."
        else
            echo "⚠️  Не удалось установить APK. Убедитесь, что Waydroid запущен ('waydroid status')."
        fi
    else
        echo "❌ Ошибка компиляции. Исправьте ошибки в коде."
    fi
    echo "👀 Ожидание изменений в исходниках..."
}

# Если передан флаг --run-once, просто выполняем один цикл и выходим
if [ "$1" = "--run-once" ]; then
    build_and_deploy
    exit 0
fi

# Первый запуск при старте скрипта
build_and_deploy

# Режим отслеживания файлов (Watch Mode)
if command -v watchexec >/dev/null 2>&1; then
    echo "⚡ Запуск watchexec для отслеживания каталога app/src..."
    exec watchexec \
        --watch app/src \
        --watch app/build.gradle.kts \
        --watch gradle/libs.versions.toml \
        --exts kt,xml,kts,toml,properties \
        --debounce 500ms \
        --restart \
        -- "$0" --run-once
elif command -v inotifywait >/dev/null 2>&1; then
    echo "⚡ Запуск inotifywait для отслеживания каталога app/src..."
    while true; do
        inotifywait -r -q -e modify,create,delete --exclude '(build/|\.git/)' app/src/ app/build.gradle.kts
        build_and_deploy
    done
else
    echo "⚠️  Утилиты watchexec или inotifywait не найдены."
    echo "Запуск встроенного непрерывного сборщика Gradle (только сборка APK):"
    exec ./gradlew.sh assembleDebug --continuous
fi
