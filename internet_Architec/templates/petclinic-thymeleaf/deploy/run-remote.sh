#!/bin/bash

# kill tomcat, delete $CATALINA_HOME/webapps/petclinic, start tomcat

# 1. kill tomcat
pid=$(ps -ef | grep tomcat | grep -v grep | awk '{print $2}')
echo "tomcat pid=$pid"
[[ -n $pid ]] && kill -9 $pid

CATALINA_HOME=/usr/local/tomcat

# 2. delete old war and unpacked directory
echo "$CATALINA_HOME/webapps"
rm -rf $CATALINA_HOME/webapps/petclinic

# 3. start tomcat
$CATALINA_HOME/bin/startup.sh

echo "run remote script done"

