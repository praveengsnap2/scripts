#!/bin/bash

check_machine_state(){
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

echo status
}

start_machine(){

check_status=`check_machine_state $1`

if [ "${check_status}" == "stopped" ] ; then
  echo "machine is in stopped state"
else
  echo "machine is not in stopped state but it is in ${check_status} state"
fi

machine_start_status=`aws ec2 start-instances --instance-ids   $1 --profile executor`

sleep 10s

flag=true

while(${flag})
do

check_status=`check_machine_state $1`
if [ "${check_status}" == "running" ] ; then
  flag=false
else
  echo "machine is not yet in running state but it is in ${check_status} state"
  sleep 5s
fi

done
}

stop_machine(){

check_status=`check_machine_state $1`

if [ "${check_status}" == "running" ] ; then
  echo "machine is in running state"
else
  echo "machine is not in running state but it is in ${check_status} state"
fi

machine_start_status=`aws ec2 start-instances --instance-ids   $1 --profile executor`

sleep 10s

flag=true

while(${flag})
do

check_status=`check_machine_state $1`
if [ "${check_status}" == "stopped" ] ; then
  flag=false
else
  echo "machine is not yet in stopped state but it is in ${check_status} state"
  sleep 5s
fi

done
}


