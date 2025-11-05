#!/usr/bin/env bash
set -euo pipefail
WS="/home/kavia/workspace/code-generation/uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406/project_backend"
cd "$WS"
[ -f /etc/profile.d/project_backend_env.sh ] && source /etc/profile.d/project_backend_env.sh || true
TMP_DIR=/tmp/project_backend_evidence
WS_EVIDENCE_DIR="$WS/.evidence"
mkdir -p "$TMP_DIR" "$WS_EVIDENCE_DIR"
LOG=/tmp/project_backend_run.log
# Ensure jar exists (build if needed)
if ! ls target/*.jar >/dev/null 2>&1; then
  mvn -B -DskipTests package || { echo "maven package failed" >&2; exit 8; }
fi
JAR=$(ls -t target/*.jar | head -n1)
[ -f "$JAR" ] || { echo "jar not found" >&2; exit 9; }
PORT=${SERVER_PORT:-8080}
# start app deterministically
nohup java -jar "$JAR" --server.port=${PORT} --management.server.port=${PORT} >"$LOG" 2>&1 &
APP_PID=$!
echo "$APP_PID" > "$TMP_DIR/app.pid"; echo "$APP_PID" > "$WS_EVIDENCE_DIR/app.pid"
cleanup(){ if ps -p "$APP_PID" >/dev/null 2>&1; then kill "$APP_PID" 2>/dev/null || true; sleep 1; if ps -p "$APP_PID" >/dev/null 2>&1; then kill -9 "$APP_PID" || true; fi; fi }
trap cleanup EXIT
# wait for health
MAX_WAIT=60; i=0
while ! curl -sS --fail "http://localhost:${PORT}/actuator/health" >/dev/null 2>&1; do
  sleep 1; i=$((i+1)); if [ $i -ge $MAX_WAIT ]; then
    echo "health endpoint not available after ${MAX_WAIT}s" >&2
    tail -n 200 "$LOG" > "$TMP_DIR/last_logs.txt" || true
    tail -n 200 "$LOG" > "$WS_EVIDENCE_DIR/last_logs.txt" || true
    ps -ef > "$TMP_DIR/processes.txt" || true
    ps -ef > "$WS_EVIDENCE_DIR/processes.txt" || true
    cleanup
    exit 10
  fi
done
HEALTH_BODY=$(curl -sS "http://localhost:${PORT}/actuator/health")
echo "$HEALTH_BODY" > "$TMP_DIR/health.json"
echo "$HEALTH_BODY" > "$WS_EVIDENCE_DIR/health.json"
# collect logs
tail -n 200 "$LOG" > "$TMP_DIR/last_logs.txt" || true
tail -n 200 "$LOG" > "$WS_EVIDENCE_DIR/last_logs.txt" || true
# stop app
cleanup
# ensure stopped
if ps -p "$APP_PID" >/dev/null 2>&1; then echo "failed to stop $APP_PID" >&2; exit 11; fi
# print summary
echo "HEALTH_RESPONSE:"; cat "$WS_EVIDENCE_DIR/health.json" || true
