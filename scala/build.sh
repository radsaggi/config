#!/bin/bash

if [[ ! $SCALA_HOME ]]
then
    echo ERROR: set a SCALA_HOME environment variable
    exit 1
else 
    echo "Found SCALA_HOME = ${SCALA_HOME}"
fi

rm -rf bin/*

echo "Compiling sources..."
scalac -feature -sourcepath src -d bin src/*
rv=$?; if [[ $rv != 0 ]]; then exit $rv; fi

# Always copy scala-library.jar to ensure binary compatiblity with built class files
echo "Fetching scala-library.jar..."
cp $SCALA_HOME/lib/scala-library.jar .
rv=$?; if [[ $rv != 0 ]]; then exit $rv; fi

echo "Building config-lib.jar..."
jar cfv ./config-lib.jar -C ./bin .
rv=$?; if [[ $rv != 0 ]]; then exit $rv; fi

echo "Complete!"
