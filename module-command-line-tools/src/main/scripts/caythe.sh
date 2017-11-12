#!/usr/bin/env sh

# JVM settings.
jvm_min_heap_space="32m"
jvm_max_heap_space="128m"
jvm_options="-Xms${jvm_min_heap_space} -Xmx${jvm_max_heap_space}"

program="${0}"

while [ -h "${program}" ]; do
  ls=`ls -ld "${program}"`
  link=`expr "${ls}" : '.*-> \(.*\)$'`

  if expr "${link}" : '.*/.*' > /dev/null; then
    program="${link}"
  else
    program=`dirname "${program}"`/"${link}"
  fi
done

java=java
if test -n "$JAVA_HOME"; then
    java="$JAVA_HOME/bin/java"
fi

if [ -n "${CAYTHE_DEBUG}" ] && [ "true" == "${CAYTHE_DEBUG}" ] ; then
    jvm_options=" -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
fi

exec "$java" ${jvm_options} -jar "${program}" "$@"
exit 1