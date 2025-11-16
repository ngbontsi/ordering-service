#!/usr/bin/env bash

set -e

echo "🔍 Looking for ZIP file in repository..."

ZIP_FILE=$(ls *.zip 2>/dev/null | head -n 1)

if [ -z "$ZIP_FILE" ]; then
  echo "❌ No ZIP file found. Upload a ZIP to the repo first."
  exit 1
fi

echo "📦 ZIP found: $ZIP_FILE"
echo "📂 Creating project folder..."

# Folder name = ZIP name without extension
FOLDER="${ZIP_FILE%.zip}"
mkdir -p "$FOLDER"

echo "📂 Extracting into: $FOLDER"
unzip -o "$ZIP_FILE" -d "$FOLDER"

echo "🗑️ Deleting ZIP file..."
rm "$ZIP_FILE"

echo "✨ Extraction complete!"
echo "Project available at: $FOLDER"