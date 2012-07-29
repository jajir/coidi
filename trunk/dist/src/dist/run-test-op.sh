#!/bin/sh

java -Dserver.role=op -Dop.conf.dir=conf -cp classes:lib/jetty-6.1.25.jar:lib/jetty-util-6.1.25.jar:lib/servlet-api-2.5-20081211.jar Start lib/test-op-${coidi.version}.war 8080
