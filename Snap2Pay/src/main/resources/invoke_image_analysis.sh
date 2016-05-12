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
#User_Id=$7

echo "${Image_File_Path} ${GPU_MACHINE}:${Destination_Dir}/${Uuid}.jpg"

scp ${Image_File_Path} ${GPU_MACHINE}:${Destination_Dir}/${Uuid}.jpg

pssh -e err -h host.txt -i -t 300 "cd /home/ubuntu/caffe; export LD_LIBRARY_PATH=/home/ubuntu/caffe/build/lib:/usr/local/lib:/usr/local/cuda/lib64:/home/ubuntu/anaconda3/lib; ./pipeline/shelfC ${Destination_Dir}/${Uuid}.jpg ${Category} ${Uuid} ${Retailer_Code} ${Store_Id} ${User_Id}""