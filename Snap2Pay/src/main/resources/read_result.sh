#!/usr/bin/env bash
WS_MACHINE="ec2-52-25-175-37.us-west-2.compute.amazonaws.com"
GPU_MACHINE="ec2-52-10-214-144.us-west-2.compute.amazonaws.com"

Destination_Dir="/tmp/processImage/"

#User_ID=$1
#Image_File_Path=$2
#Category=$3
#Uuid=$4
#Retailer_Code=$5
#Store_Id=$6

scp ${Image_File_Path} ${GPU_MACHINE}:${Destination_Dir}/${Uuid}.jpg

pssh -e err -h host.txt -i "cat ${Destination_Dir}/${imageUUID}.json "
