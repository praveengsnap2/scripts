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

echo "Making call to get jobs api curl http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/getImageUuidListByStatus?"
RESULT=`curl http://"${WS_MACHINE}":8080/Snap2Pay-2.0/service/S2P/getImageUuidListByStatus?`
if [ $? -ne 0 ]; then
   echo "RESULT=${RESULT}"
   echo "Failed: http://${WS_MACHINE}:8080/Snap2Pay-2.0/service/S2P/getImageUuidListByStatus"
   #MSG="Failed: http://${WS_MACHINE}:8080/Snap2Pay-2.0/service/S2P/getImageUuidListByStatus"
   #send_mail "$MSG"
   exit 1
fi

#
#echo "${RESULT} | jq -r '.ResultSet.row.imageUUID'"

imageUUID=` echo "${RESULT}" | jq -r '.ResultSet.row.imageUUID'`

echo "imageUUID=${imageUUID}"

if [ "${remainingJob}" == "null" ]; then
    loop="1"
    echo "no more jobs to run processing last job "
fi

#rm $imageUUID
#if [ $? -ne 0 ]; then
#   echo "Failed: remove of image file failed "
#   exit 1
#fi

echo "Making call to get Image api   http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/getImage?userId=$userId&imageUUID=$imageUUID"
curl -O -J "http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/getImage?userId=$userId&imageUUID=$imageUUID"
if [ $? -ne 0 ]; then
   echo "Failed: http://${WS_MACHINE}:8080/Snap2Pay-2.0/service/S2P/getImage?userId=${userId}&imageUUID=${imageUUID}"
   #MSG="Failed: http://${WS_MACHINE}:8080/Snap2Pay-2.0/service/S2P/getImage?userId=${userId}&imageUUID=${imageUUID}"
   #send_mail  "$MSG"
   exit 1
fi

imageFileCount=`ls $imageUUID | wc -l`
echo "imageFileCount=$imageFileCount"

if [ "${imageFileCount}" == "1" ]; then
       echo "unable to find downloaded image "
       #MSG="unable to find downloaded image "
       #send_mail  "$MSG"
       exit 1
fi


#run image processing code
#
#
#
#
#
#store the result json in a file name Result/${imageUUID}
#
#
#
#
#{ "status": 200, "storeId": "sh01", "categoryId":"test", "date": "20150807", "imageUUID": "2a7ba3fd-d666-4b43-aa7e-3efa6aa746af", "skus": [ { "upc": "LO.TRRE.C", "pog": "3", "osa": "1", "facings": "3", "priceLabel": "1" },{ "upc": "LO.VF.S", "pog": "2", "osa": "1", "facings": "2", "priceLabel": "0" },{ "upc": "LO.TOREE.S", "pog": "2", "osa": "1", "facings": "2", "priceLabel": "0" },{ "upc": "LO.CV.C", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "0" },{ "upc": "LO.TOREE.C", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "0" },{ "upc": "LO.TRRE.S", "pog": "4", "osa": "1", "facings": "4", "priceLabel": "1" },{ "upc": "LO.TORE5.S", "pog": "2", "osa": "1", "facings": "2", "priceLabel": "1" },{ "upc": "LO.TORE5.C", "pog": "2", "osa": "1", "facings": "2", "priceLabel": "1" },{ "upc": "LO.SI.C", "pog": "2", "osa": "1", "facings": "2", "priceLabel": "1" },{ "upc": "LO.SIU.S", "pog": "2", "osa": "1", "facings": "2", "priceLabel": "1" },{ "upc": "LO.CV.S", "pog": "3", "osa": "-2", "facings": "0", "priceLabel": "0" },{ "upc": "TR.CR.S", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "0" },{ "upc": "TR.MR.S", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "1" },{ "upc": "TR.MR.C", "pog": "2", "osa": "1", "facings": "1", "priceLabel": "1" },{ "upc": "TR.AB.C", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "1" },{ "upc": "PN.CC.S", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "0" },{ "upc": "PN.MR.S", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "0" },{ "upc": "PN.SV.S", "pog": "1", "osa": "0", "facings": "0", "priceLabel": "0" },{ "upc": "TR.CR.C", "pog": "1", "osa": "1", "facings": "1", "priceLabel": "0" } ] }

dataFile=Result/${imageUUID}

echo "Making call to store result api   curl -d @$dataFile  -H 'Content-Type: Application/Json' -X POST 'http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/storeShelfAnalysis?'"
StoreResult=`curl -d @$dataFile  -H 'Content-Type: Application/Json' -X POST "http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/storeShelfAnalysis?"`
if [ $? -ne 0 ]; then
   echo "Failed: curl   -d @$dataFile  -H "Content-Type: Application/Json" -X POST 'http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/storeShelfAnalysis?"
   #MSG="Failed: curl   -d @$dataFile  -H "Content-Type: Application/Json" -X POST 'http://$WS_MACHINE:8080/Snap2Pay-2.0/service/S2P/storeShelfAnalysis?"
   #send_mail "$MSG"
   exit 1
fi

echo "StoreResult=$StoreResult"

status=` echo "${StoreResult}" | jq -r '.ResultSet.row.responseCode'`

if [ "${status}" == "200" ]; then
    echo "store api call success "
    continue
else {
    echo "store api call failed  "
       #MSG="store api call failed "
       #send_mail  "$MSG"
       exit 1
}
fi
loop="1"
done