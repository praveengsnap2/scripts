#!/usr/bin/env bash
#filepath=$1
#thumbnailPath=$2
#echo ${filepath} ${thumbnailPath} ${imageRotation}
result=`java -cp javaxt-core.jar:. Compress ${filepath} ${thumbnailPath} ${imageRotation}`
echo ${result}