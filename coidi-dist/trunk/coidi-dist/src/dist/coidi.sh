#!/bin/sh
#
# Start stop script. Following commands could be called:
#
# start
# stop
# help
#
#
#
#

if [ ! -a log ] 
then
	mkdir log
fi


#
# Kill all processes containing parameter value
#
find_and_kill () {
	if [ $1 == "" ]
	then
		echo "missing parameter <process name>"
	else
		for i in `ps ax | grep SNAPSHOT | grep -v grep | awk '{print $1}'`
		do
  			kill -9 $i
		done
	fi
}


start () {
	echo "starting coidi"
	./run-test-op.sh > ./log/op.log 2>&1 &
	./run-test-rp.sh > ./log/rp.log 2>&1 &
}

stop () {
	echo "stopping coidi"
	find_and_kill coidi-test-op-1.0-SNAPSHOT.war
#	find_and_kill coidi-test-or-1.0-SNAPSHOT.war
}

help () {
	echo "help"
}

case "$1" in
   start)
      start
      ;;
   stop)
      stop
      ;;
   help)
      help
      ;;
   *)
      SCRIPTNAME=`basename $0`
      echo "Usage: $SCRIPTNAME {start|stop|help}"
      exit 1
      ;;
esac

exit 0

