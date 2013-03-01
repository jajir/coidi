#!/bin/sh
#
# Starts OpenID relaying party
#

java -Dserver.role=rp \
     -Drp.conf.dir=conf \
     -jar lib/jetty-runner-8.1.9.v20130131.jar \
     --port 8081 ./lib/test-rp-1.0-SNAPSHOT.war
