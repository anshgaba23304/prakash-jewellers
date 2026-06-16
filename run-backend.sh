#!/usr/bin/env bash
set -e

# Pin JDK 21 (override JAVA_HOME here if your JDK lives elsewhere)
if [ -d "/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home" ]; then
  export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home"
  export PATH="$JAVA_HOME/bin:$PATH"
fi

cd "$(dirname "$0")/backend"
echo "Starting Prakash Jewellers backend on http://localhost:8080 ..."
mvn spring-boot:run
