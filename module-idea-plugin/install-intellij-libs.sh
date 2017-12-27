#!/usr/bin/env bash

#
# This script will install all files in IntelliJ IDEA's lib/ folder to the local
# maven .m2 repository. This way we can use them during the build.
#
# Usage:
#   ./install-intellij-libs.sh 13.1.4 /Users/ahe/Applications/IntelliJ-IDEA-13.app/
#

set -e
set -u

usage="Usage: ${0} <VERSION> /path/to/intellij"
example="Example: ${0} 2017.3.1 /Application/IntelliJ IDEA.app"

ideaVersion=${1:-}
intellijHome=${2:-}

if [ "${ideaVersion}" = "" ]; then
    echo "Version given!"
    echo "${usage}"
    echo "${example}"
    exit 1
fi

if [ "${intellijHome}" == "" ]; then
    echo "No IntelliJ home given!"
    echo "${usage}"
    echo "${example}"
    exit 2
fi

if [ ! -d "${intellijHome}" ]; then
    echo "Directory does not exist: ${intellijHome}!"
    echo "${usage}"
    echo "${example}"
  exit 2
fi

echo "Installing IntelliJ artifacts to Maven local repository..."
libs="boot openapi idea extensions annotations util"

for artifact in ${libs}; do
    mvn install:install-file \
        -Dfile="${intellijHome}/Contents/lib/${artifact}.jar" \
        -DgroupId=com.intellij -DartifactId=${artifact} \
        -Dversion=${ideaVersion} \
        -Dpackaging=jar
done
