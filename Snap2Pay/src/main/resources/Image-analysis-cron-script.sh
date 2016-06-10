#!/bin/bash
#this script need jq to be installed
#WS_MACHINE="ec2-52-25-175-37.us-west-2.compute.amazonaws.com"
WS_MACHINE="localhost"
HOST_ID="AWS1"


echo "checking for already running job"

jobRunning=`ps aux |grep "sh Image-analysis-cron-script.sh >> Image-analysis-cron-script.log" |grep -v "grep" |grep -v "tail" | wc -l`
if [ "${jobRunning}" -gt "2" ]; then
       echo "One instance of job is already running "
       exit 1
else {
	    echo "no instance of job is running moving ahead"
     }
fi

echo "Making call to process next Image api  curl http://$WS_MACHINE:8080/Snap2Pay-1.0/service/S2P/processNextImage?"
RESULT=`curl http://"${WS_MACHINE}":8080/Snap2Pay-1.0/service/S2P/processNextImage?`
if [ $? -ne 0 ]; then
   echo "RESULT=${RESULT}"
   echo "Failed: http://${WS_MACHINE}:8080/Snap2Pay-1.0/service/S2P/processNextImage?"
   exit 1
fi

echo "RESULT=${RESULT}"
