#!/bin/bash

# copy petclinic.war from windows and run in tomcat

LOCAL_APP_PATH=E:/source/practice-java/petclinic-thymeleaf
WAR_NAME=petclinic.war

REMOTE=root@192.168.1.201
REMOTE_WEBAPP_DIR=${REMOTE}:/usr/local/tomcat/webapps

function mvn_package {
    cd $LOCAL_APP_PATH
    mvn package -DskipTests
    echo "package done"
}


function upload {
    cd $LOCAL_APP_PATH
    scp ./target/$WAR_NAME $REMOTE_WEBAPP_DIR/$WAR_NAME
    echo "uploaded: $REMOTE_WEBAPP_DIR/$WAR_NAME"
}

mvn_package

upload

FILE=$LOCAL_APP_PATH/deploy/run-remote.sh
[[ -f "$FILE" ]] && ssh  $REMOTE /bin/bash < $FILE

