#!/usr/bin/env bash
set -euo pipefail
WS="/home/kavia/workspace/code-generation/uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406/project_backend"
cd "$WS"
[ -f /etc/profile.d/project_backend_env.sh ] && source /etc/profile.d/project_backend_env.sh || true
# idempotent build: skip if target jar exists and newer than sources
if ls target/*.jar >/dev/null 2>&1; then
  NEWER=$(find src pom.xml -type f -newer $(ls -t target/*.jar | head -n1) || true)
  if [ -z "$NEWER" ]; then
    exit 0
  fi
fi
mvn -B -DskipTests package
