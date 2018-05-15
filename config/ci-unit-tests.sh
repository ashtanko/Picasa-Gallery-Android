#!/usr/bin/env bash

set -e

echo "TEST_TYPE=$TEST_TYPE"
echo "EMULATOR_API=$EMULATOR_API"
echo "ABI=$ABI"

if [ "$TEST_TYPE" == "unit" ]; then
  echo "Starting unit tests..."
  ./gradlew clean build lint -Pbuild=dev -PdisablePreDex --stacktrace createDevDebugUnitTestCoverageReport createDevDebugUnitTestCoverageReportKotlin coveralls;
elif [ "$TEST_TYPE" == "instrumentation" ]; then
  echo "Skipping unit tests for instrumentation builds."
else
  echo "Unknown test type"
  exit 1
fi