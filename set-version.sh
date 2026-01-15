#! /usr/bin/env bash 

#
# Copyright contributors to the Galasa project
#
# SPDX-License-Identifier: EPL-2.0
#

#-----------------------------------------------------------------------------------------                   
#
# Objectives: Sets the version number of this component.
#
# Environment variable over-rides:
# None
# 
#-----------------------------------------------------------------------------------------                   

# Where is this script executing from ?
BASEDIR=$(dirname "$0");pushd $BASEDIR 2>&1 >> /dev/null ;BASEDIR=$(pwd);popd 2>&1 >> /dev/null
export ORIGINAL_DIR=$(pwd)

cd "${BASEDIR}/.."
WORKSPACE_DIR=$(pwd)

#-----------------------------------------------------------------------------------------                   
#
# Set Colors
#
#-----------------------------------------------------------------------------------------                   
bold=$(tput bold)
underline=$(tput sgr 0 1)
reset=$(tput sgr0)
red=$(tput setaf 1)
green=$(tput setaf 76)
white=$(tput setaf 7)
tan=$(tput setaf 202)
blue=$(tput setaf 25)

#-----------------------------------------------------------------------------------------                   
#
# Headers and Logging
#
#-----------------------------------------------------------------------------------------                   
underline() { printf "${underline}${bold}%s${reset}\n" "$@" ;}
h1() { printf "\n${underline}${bold}${blue}%s${reset}\n" "$@" ;}
h2() { printf "\n${underline}${bold}${white}%s${reset}\n" "$@" ;}
debug() { printf "${white}%s${reset}\n" "$@" ;}
info() { printf "${white}➜ %s${reset}\n" "$@" ;}
success() { printf "${green}✔ %s${reset}\n" "$@" ;}
error() { printf "${red}✖ %s${reset}\n" "$@" ;}
warn() { printf "${tan}➜ %s${reset}\n" "$@" ;}
bold() { printf "${bold}%s${reset}\n" "$@" ;}
note() { printf "\n${underline}${bold}${blue}Note:${reset} ${blue}%s${reset}\n" "$@" ;}
#-----------------------------------------------------------------------------------------                   
# Functions
#-----------------------------------------------------------------------------------------                   
function usage {
    h1 "Syntax"
    cat << EOF
set-version.sh [OPTIONS]
Options are:
-v | --version xxx : Mandatory. Set the version number to something explicitly. 
    For example '--version 0.46.0'
EOF
}
#-----------------------------------------------------------------------------------------                   
# Process parameters
#-----------------------------------------------------------------------------------------                   
component_version=""

while [ "$1" != "" ]; do
    case $1 in
        -v | --version )        shift
                                export component_version=$1
                                ;;
        -h | --help )           usage
                                exit
                                ;;
        * )                     error "Unexpected argument $1"
                                usage
                                exit 1
    esac
    shift
done

if [[ -z $component_version ]]; then 
    error "Missing mandatory '--version' argument."
    usage
    exit 1
fi

temp_dir=$BASEDIR/temp/version_bump
rm -rf $temp_dir
mkdir -p $temp_dir

# Bump versions up in the top-level build.gradle
cat $BASEDIR/galasa-inttests-parent/build.gradle \
    | sed "s/('dev\.galasa:galasa-bom:.*')/('dev.galasa:galasa-bom:$component_version')/1" \
    | sed "s/'dev\.galasa\.githash' version '.*'/'dev.galasa.githash' version '$component_version'/1" \
    | sed "s/'dev\.galasa\.testcatalog' version '.*'/'dev.galasa.testcatalog' version '$component_version'/1" \
    | sed "s/'dev\.galasa\.tests' version '.*'/'dev.galasa.tests' version '$component_version'/1" \
    > $temp_dir/top-build.gradle
cp $temp_dir/top-build.gradle $BASEDIR/galasa-inttests-parent/build.gradle

# Bump the galasaVersion property in the gradle.properties
cat $BASEDIR/galasa-inttests-parent/gradle.properties | sed "s/galasaVersion=.*/galasaVersion=$component_version/1" > $temp_dir/top-gradle.properties
cp $temp_dir/top-gradle.properties $BASEDIR/galasa-inttests-parent/gradle.properties

# Bump the inttests test project version up in the test project's build.gradle
cat $BASEDIR/galasa-inttests-parent/dev.galasa.inttests/build.gradle | sed "s/version = '.*'/version = '$component_version'/1" > $temp_dir/inttests-build.gradle
cp $temp_dir/inttests-build.gradle $BASEDIR/galasa-inttests-parent/dev.galasa.inttests/build.gradle

# Bump the versions in the OBR's pom.xml
cat $BASEDIR/galasa-inttests-parent/dev.galasa.inttests.obr/pom.xml \
    | sed "/dev\.galasa\.inttests\.obr<\/artifactId>/{n;s/<version>[^<]*<\/version>/<version>$component_version<\/version>/;}" \
    | sed "/dev\.galasa\.inttests<\/artifactId>/{n;s/<version>[^<]*<\/version>/<version>$component_version<\/version>/;}" \
    | sed "/galasa-maven-plugin<\/artifactId>/{n;s/<version>[^<]*<\/version>/<version>$component_version<\/version>/;}" \
    > $temp_dir/obr-pom.xml
cp $temp_dir/obr-pom.xml $BASEDIR/galasa-inttests-parent/dev.galasa.inttests.obr/pom.xml