#!/usr/bin/env bash
set -euo pipefail
WS="/home/kavia/workspace/code-generation/uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406/project_backend"
mkdir -p "$WS"
# robust java detection: prefer javac then java
JAVA_FOUND=0
JAVA_BIN=""
for cmd in javac java; do
  if command -v "$cmd" >/dev/null 2>&1; then JAVA_BIN=$(readlink -f "$(command -v $cmd)"); JAVA_FOUND=1; break; fi
done
MAJOR=0
if [ "$JAVA_FOUND" -eq 1 ]; then
  VER_STR=$( (java -version 2>&1) || (javac -version 2>&1) || true )
  MAJOR=$(printf "%s" "$VER_STR" | awk -F '"' '/version/ {print $2} !/version/ {print $0}' | sed -n '1p' | sed -E 's/[^0-9]*([0-9]+).*/\1/' || echo 0)
fi
# decide whether to install openjdk-17 and maven
INSTALL_PKGS=0
if [ "$MAJOR" -lt 17 ]; then INSTALL_PKGS=1; fi
if ! command -v mvn >/dev/null 2>&1; then INSTALL_PKGS=1; fi
if [ "$INSTALL_PKGS" -eq 1 ]; then
  sudo apt-get update -q && sudo DEBIAN_FRONTEND=noninteractive apt-get install -y -q openjdk-17-jdk maven || { echo "apt install failed" >&2; exit 2; }
  if command -v java >/dev/null 2>&1; then JAVA_BIN=$(readlink -f "$(command -v java)"); fi
fi
[ -n "$JAVA_BIN" ] || { echo "java not found after install" >&2; exit 3; }
JAVA_HOME_DIR=$(dirname "$(dirname "$JAVA_BIN")")
MAVEN_BIN=$(command -v mvn || true)
MAVEN_DIR=""
if [ -n "$MAVEN_BIN" ]; then MAVEN_DIR=$(dirname "$(readlink -f "$MAVEN_BIN")"); fi
PROFILE_FILE=/etc/profile.d/project_backend_env.sh
TMPF=$(mktemp)
cat > "$TMPF" <<EOF
# project_backend tool environment (non-secret)
export JAVA_HOME="${JAVA_HOME_DIR}"
# prepend java and maven bins if not already present
if ! echo "\$PATH" | /bin/grep -q "${JAVA_HOME_DIR}/bin"; then PATH="${JAVA_HOME_DIR}/bin:\$PATH"; fi
EOF
if [ -n "$MAVEN_DIR" ]; then cat >> "$TMPF" <<EOF
if ! echo "\$PATH" | /bin/grep -q "${MAVEN_DIR}"; then PATH="${MAVEN_DIR}:\$PATH"; fi
EOF
fi
cat >> "$TMPF" <<'EOF'
export PATH
EOF
sudo install -m 0755 -o root -g root -D "$TMPF" "$PROFILE_FILE" >/dev/null 2>&1 || (sudo cp "$TMPF" "$PROFILE_FILE" && sudo chmod 755 "$PROFILE_FILE")
rm -f "$TMPF"
# create .env.sample in workspace (do not store secrets here)
cat > "$WS/.env.sample" <<'ENV'
# runtime env example (DO NOT store secrets in /etc/profile.d)
SPRING_PROFILES_ACTIVE=dev
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
JWT_SECRET=change-me
UPLOADS_DIR=${UPLOADS_DIR:-./uploads}
SERVER_PORT=8080
ENV
# source profile for current shell if possible
# shellcheck disable=SC1090
if [ -f "$PROFILE_FILE" ]; then source "$PROFILE_FILE" || true; fi
java -version >/dev/null 2>&1 || { echo "java not usable" >&2; exit 4; }
mvn -v >/dev/null 2>&1 || { echo "maven not usable" >&2; exit 5; }
