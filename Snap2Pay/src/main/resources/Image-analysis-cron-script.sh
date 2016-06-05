#!/bin/bash
#this script need jq to be installed
#WS_MACHINE="ec2-52-25-175-37.us-west-2.compute.amazonaws.com"
WS_MACHINE="localhost"
HOST_ID="AWS1"

#
#function send_mail ()
#{
#FROM="snap2buy@${HOST_ID}.com"
#TO="different.sachin@gmail.com"
#REPLYTO="different.sachin@gmail.com"
#SUBJECT="image processing failed"
#MAIL_MSG="${HOST_ID}--$1"
#echo "`date`: msg---$MAIL_MSG"
#
#echo "From:$FROM
#To:$TO
#Reply-To:$REPLYTO
#Subject:$SUBJECT
#
#$MAIL_MSG
#
#This is system generated email, please do not reply.
#AGI Daemon
#" | /usr/sbin/sendmail -t different.sachin@gmail.com
#echo "`date`: Mail sent for-- $1"
#}


echo "checking for already running job"

jobRunning=`ps aux |grep "Image-analysis-cron-script" |grep -v "grep" |wc -l`
if [ "${jobRunning}" -gt "2" ]; then
       echo "One instance of job is already running "
       #MSG="One instance of job is already running "
       #send_mail "$MSG"
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
   #MSG="Failed: http://${WS_MACHINE}:8080/Snap2Pay-1.0/service/S2P/processNextImage?"
   #send_mail "$MSG"
   exit 1
fi

echo "RESULT=${RESULT}"
