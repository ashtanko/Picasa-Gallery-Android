#!/usr/bin/env bash

set -e

echo "TEST_TYPE=$TEST_TYPE"
echo "EMULATOR_TAG=$EMULATOR_TAG"
echo "EMULATOR_API=$EMULATOR_API"
echo "ABI=$ABI"

if [ "$TEST_TYPE" == "instrumentation" ] ; then
  echo "Starting AVD for API $EMULATOR_API"
  ./ci-create-avd.sh &
else
  echo "Skipping AVD for non-instrumentation build"
fi

exit 0