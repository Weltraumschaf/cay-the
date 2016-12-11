#!/usr/bin/env sh

# JVM settings.
JVM_MIN_HEAP_SPACE="32m"
JVM_MAX_HEAP_SPACE="128m"
JVM_OPTIONS="-Xms${JVM_MIN_HEAP_SPACE} -Xmx${JVM_MAX_HEAP_SPACE}"

PROGRAM="${0}"

while [ -h "${PROGRAM}" ]; do
  LS=`ls -ld "${PROGRAM}"`
  LINK=`expr "${LS}" : '.*-> \(.*\)$'`

  if expr "${LINK}" : '.*/.*' > /dev/null; then
    PROGRAM="${LINK}"
  else
    PROGRAM=`dirname "${PROGRAM}"`/"${LINK}"
  fi
done

PROGRAM_DIRECTORY=`dirname "${PROGRAM}"`

JAR="${PROGRAM_DIRECTORY}/caythe.jar"

if [ ! -f "${JAR}" ] ; then
    PROJECT_DIR=`dirname "${PROGRAM_DIRECTORY}"`
    echo "ERROR: JAR file ${JAR} not present!"
    echo "Invoke 'mvn clean install' in the project base directory: ${PROJECT_DIR}."
    exit 1
fi

$JAVA_HOME/bin/java ${JVM_OPTIONS} -jar "${JAR}" "$@"
