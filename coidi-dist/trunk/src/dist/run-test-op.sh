#!/bin/sh
#
# Starts OpenID provider
#

java -Dserver.role=op \
     -Dop.conf.dir=conf \
     -jar lib/jetty-runner-8.1.9.v20130131.jar \
     --port 8080 ./lib/test-rp-1.0-SNAPSHOT.war
     
