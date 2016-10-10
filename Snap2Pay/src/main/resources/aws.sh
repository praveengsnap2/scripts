#!/bin/bash
#this script need jq to be installed
#WS_MACHINE="ec2-52-25-175-37.us-west-2.compute.amazonaws.com"
WS_MACHINE="localhost"

HOST_1="i-ba9e447d"
HOST_2="i-939d4d06"
HOST_3="i-05f14990"
HOST_4="i-922ba38a"
HOST_5="i-932ba38b"


echo "********************************"
echo "checking for already running job"

jobRunning=`ps aux |grep "/bin/bash aws.sh ${hostId} >> aws-${hostId}.log" |grep -v "grep" |grep -v "tail" | wc -l`
if [ "${jobRunning}" -gt "1" ]; then
       echo "jobRunning = ${jobRunning} instance of job is already running "
       exit 1
else {
	    echo "jobRunning = ${jobRunning} instance of job is running moving ahead"
     }
fi

check_machine_state(){
status=stopped
pending=`aws ec2 describe-instance-status --instance-id  $1 --profile executor  | grep INSTANCESTATE |grep  0 |wc -l `
running=`aws ec2 describe-instance-status --instance-id  $1 --profile executor  | grep INSTANCESTATE |grep  16 |wc -l`
shutting_down=`aws ec2 describe-instance-status --instance-id  $1 --profile executor  | grep INSTANCESTATE |grep  32|wc -l`
terminated=`aws ec2 describe-instance-status --instance-id  $1 --profile executor  | grep INSTANCESTATE |grep  48 |wc -l`
stopping=`aws ec2 describe-instance-status --instance-id  $1 --profile executor  | grep INSTANCESTATE |grep  64 |wc -l`
stopped=`aws ec2 describe-instance-status --instance-id  $1 --profile executor  | grep INSTANCESTATE |grep  80 |wc -l`

if [ "${pending}" == "1" ]
  then
    status="pending";
  else if [ "${running}" == "1" ]
    then
      status="running";
    else if [ "${shutting_down}" == "1" ]
      then
        status="shutting-down";
      else if [ "${terminated}" == "1" ]
        then
          status="terminated";
        else if [ "${stopping}" == "1" ]
          then
            status="stopping";
          else if [ "${stopped}" == "1" ]
            then
              status="stopped";
          fi
        fi
      fi
    fi
  fi
fi

echo $status
}

start_machine(){
check_status=`check_machine_state $1`
if [ "${check_status}" == "stopped" ] ; then
  echo "$1 machine is in stopped state"
  echo `aws ec2 start-instances --instance-ids   $1 --profile executor`
  sleep 10s
  flag=true
  while(${flag})
  do
  check_new_status=`check_machine_state $1`
  if [ "${check_new_status}" == "running" ] ; then
    flag=false
  else
    echo "machine is not yet started, but it is in ${check_new_status} state"
    sleep 5s
  fi
  done
else
  echo "START SUCCESS ==> ${1} machine is in ${check_status} state"
fi
}

stop_machine(){
check_status=`check_machine_state $1`
if [ "${check_status}" == "running" ] ; then
  echo "$1 machine is in running state"
  echo `aws ec2 stop-instances --instance-ids   $1 --profile executor`
  sleep 10s
  flag=true
  while(${flag})
  do
  check_new_status=`check_machine_state $1`
  if [ "${check_new_status}" == "stopped" ] ; then
    flag=false
  else
    echo "${1} machine is not yet stopped, but it is in ${check_new_status} state"
    sleep 5s
  fi
  done
else
  echo "STOP SUCCESS ==> ${1} machine is in ${check_status} state"
fi
}

echo "Making call to get jobs api curl http://$WS_MACHINE:8080/Snap2Buy-2.0/service/S2B/getCronJobCount?hostId=${hostId}"
RESULT=`curl http://"${WS_MACHINE}":8080/Snap2Buy-2.0/service/S2B/getCronJobCount?hostId="${hostId}"`
if [ $? -ne 0 ]; then
   echo "RESULT=${RESULT}"
   echo "Failed: http://${WS_MACHINE}:8080/Snap2Buy-2.0/service/S2B/getCronJobCount?hostId=${hostId}"
   exit -1
fi

#RESULT=`cat i.txt`

if [ "$RESULT" -eq 0 ]; then
    echo "0"
    stop_machine $HOST_1
    stop_machine $HOST_2
    stop_machine $HOST_3
    stop_machine $HOST_4
    stop_machine $HOST_5
    exit 0
    else if [ "$RESULT" -lt 25 ]; then
        echo "0-25"
        start_machine $HOST_1
        stop_machine $HOST_2
        stop_machine $HOST_3
        stop_machine $HOST_4
        stop_machine $HOST_5
        else if [ "$RESULT" -lt 50 ]; then
            echo "25-50"
            start_machine $HOST_1
            start_machine $HOST_2
            stop_machine $HOST_3
            stop_machine $HOST_4
            stop_machine $HOST_5
            else if [ "$RESULT" -lt 75 ]; then
                echo "50-75"
                start_machine $HOST_1
                start_machine $HOST_2
                start_machine $HOST_3
                stop_machine $HOST_4
                stop_machine $HOST_5
                else if [ "$RESULT" -lt 100 ]; then
                    echo "75-100"
                    start_machine $HOST_1
                    start_machine $HOST_2
                    start_machine $HOST_3
                    start_machine $HOST_4
                    stop_machine $HOST_5
                    else if [ "$RESULT" -gt 100 ]; then
                        echo "100-..."
                        start_machine $HOST_1
                        start_machine $HOST_2
                        start_machine $HOST_3
                        start_machine $HOST_4
                        start_machine $HOST_5
                        else
                          exit -1
                        fi
                    fi
                fi
            fi
        fi
    fi