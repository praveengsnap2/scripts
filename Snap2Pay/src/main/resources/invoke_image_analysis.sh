#!/usr/bin/env bash
WS_MACHINE="ec2-52-25-175-37.us-west-2.compute.amazonaws.com"
#hostId="52.40.228.141"

Destination_Dir="/data/processImage/"

#Image_File_Path=$2
#Category=$3
#Uuid=$4
#Retailer_Code=$5
#Store_Id=$6
#User_Id=$7
#Project_type_id=$8

echo "${Image_File_Path} ${hostId}:${Destination_Dir}/${Uuid}.jpg"

scp ${Image_File_Path} ${hostId}:${Destination_Dir}/${Uuid}.jpg

echo "pssh -e err -H ${hostId} -i -t 600 cd /home/ubuntu/caffe; export LD_LIBRARY_PATH=/home/ubuntu/caffe/build/lib:/usr/local/lib:/usr/local/cuda/lib64:/home/ubuntu/anaconda3/lib; ./pipeline/shelfC ${Destination_Dir}/${Uuid}.jpg ${Category} ${Uuid} ${Retailer_Code} ${Store_Id}  ${User_Id} ${Project_type_id}"

/usr/local/bin/pssh -e err -H ${hostId} -i -t 600 "cd /home/ubuntu/caffe; export LD_LIBRARY_PATH=/home/ubuntu/caffe/build/lib:/usr/local/lib:/usr/local/cuda/lib64:/home/ubuntu/anaconda3/lib; ./pipeline/shelfC ${Destination_Dir}/${Uuid}.jpg ${Category} ${Uuid} ${Retailer_Code} ${Store_Id}  ${User_Id} ${Project_type_id}"