#!/usr/bin/env bash
set -euo pipefail
WS="/home/kavia/workspace/code-generation/uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406/project_backend"
cd "$WS"
[ -f /etc/profile.d/project_backend_env.sh ] && source /etc/profile.d/project_backend_env.sh || true
JAR=$(ls -t target/*.jar 2>/dev/null | head -n1 || true)
if [ -z "$JAR" ] || [ ! -f "$JAR" ]; then
  echo "jar not found, run build first" >&2
  exit 9
fi
PORT=${SERVER_PORT:-8080}
TMP_DIR=/tmp/project_backend_evidence
WS_EVIDENCE_DIR="$WS/.evidence"
mkdir -p "$TMP_DIR" "$WS_EVIDENCE_DIR"
LOG=/tmp/project_backend_run.log
nohup java -jar "$JAR" --server.port=${PORT} --management.server.port=${PORT} >"$LOG" 2>&1 &
APP_PID=$!
echo "$APP_PID" > "$TMP_DIR/app.pid"
echo "$APP_PID" > "$WS_EVIDENCE_DIR/app.pid"
cat > "$WS/.stop_app.sh" <<'EOF'
#!/usr/bin/env bash
set -euo pipefail
PID_FILE="/tmp/project_backend_evidence/app.pid"
if [ -f "$PID_FILE" ]; then
  PID=$(cat "$PID_FILE")
  if ps -p "$PID" >/dev/null 2>&1; then
    kill "$PID" 2>/dev/null || true
    sleep 1
    if ps -p "$PID" >/dev/null 2>&1; then
      kill -9 "$PID" || true
    fi
  fi
  rm -f "$PID_FILE"
fi
EOF
chmod +x "$WS/.stop_app.sh"
# print PID for caller
echo "$APP_PID"
