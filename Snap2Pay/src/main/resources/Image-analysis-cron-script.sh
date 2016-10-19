#!/bin/bash
#this script need jq to be installed
#WS_MACHINE="ec2-52-25-175-37.us-west-2.compute.amazonaws.com"
WS_MACHINE="localhost"
hostId=$1


echo "********************************"
echo "checking for already running job"

jobRunning=`ps aux |grep "sh Image-analysis-cron-script.sh ${hostId} >> Image-analysis-cron-script-${hostId}.log" |grep -v "grep" |grep -v "tail" | wc -l`
if [ "${jobRunning}" -gt "1" ]; then
       echo "jobRunning = ${jobRunning} instance of job is already running "
       exit 1
else {
            echo "jobRunning = ${jobRunning} instance of job is running moving ahead"
     }
fi


while(true)
do

echo "********************************"
echo "checking if machine is responding"
CHECK=`/usr/local/bin/pssh -H ${hostId} -i "echo SUCCESS"`
echo "check Status = ${CHECK}"
SUCCESS="SUCCESS"
if echo "${CHECK}" | grep -q "${SUCCESS}"
then
    echo "Continue :: Machine is Up"
else
    echo "Existing :: Machine Down"; exit 2;
fi

echo "********************************"
echo "Making call to process next Image api  curl http://$WS_MACHINE:8080/Snap2Buy-2.0/service/S2B/processNextImage?hostId=${hostId}"
RESULT=`curl http://"${WS_MACHINE}":8080/Snap2Buy-2.0/service/S2B/processNextImage?hostId=${hostId}`
if [ $? -ne 0 ]; then
   echo "RESULT=${RESULT}"
   echo "Failed: http://${WS_MACHINE}:8080/Snap2Buy-2.0/service/S2B/processNextImage?hostId=${hostId}"
   exit 1
fi


#echo "RESULT=${RESULT}"
ERROR_STRING1="no more job left to process"
if echo "${RESULT}" | grep -q "${ERROR_STRING1}"
then
    echo "sleeping for 30 sec";
    sleep 30s;
else
    echo "Continue"
fi
done