 #!/usr/bin/env bash

set -e

echo "🔍 Checking for nested project folder..."

# Detect the only folder inside the current directory
MAIN_FOLDER=$(find . -maxdepth 1 -type d ! -name "." | head -n 1)

if [ -z "$MAIN_FOLDER" ]; then
  echo "❌ No project folder found."
  exit 1
fi

echo "📁 Outer folder detected: $MAIN_FOLDER"

# Detect nested folder inside it
INNER_FOLDER=$(find "$MAIN_FOLDER" -maxdepth 1 -type d ! -name "$MAIN_FOLDER" | head -n 1)

if [ -z "$INNER_FOLDER" ]; then
  echo "✅ No nested folder. Structure is already clean."
  exit 0
fi

echo "📂 Nested folder found: $INNER_FOLDER"
echo "📤 Moving contents up..."

# Move everything out of nested folder
mv "$INNER_FOLDER"/* "$MAIN_FOLDER"/

echo "🗑️ Removing nested folder..."
rm -rf "$INNER_FOLDER"

echo "✨ Folder flattening complete!"