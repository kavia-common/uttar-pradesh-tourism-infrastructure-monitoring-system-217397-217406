#!/usr/bin/env bash
set -euo pipefail
WS="/home/kavia/workspace/code-generation/uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406/project_backend"
cd "$WS"
[ -f /etc/profile.d/project_backend_env.sh ] && source /etc/profile.d/project_backend_env.sh || true
mvn -B test
