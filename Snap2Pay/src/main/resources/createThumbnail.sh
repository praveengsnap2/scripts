#!/usr/bin/env bash



#
#filepath=$1
#thumbnailPath=$2
#imageRotation=$3


#echo ${filepath} ${thumbnailPath} ${imageRotation}
#result=`java -cp javaxt-core.jar:. Compress ${filepath} ${thumbnailPath} ${imageRotation}`
#echo ${result}




/usr/bin/convert ${filepath} -resize 900 -rotate ${imageRotation} ${thumbnailPath}

orig=`convert ${filepath} -ping -format "%w x %h" info:`
new=`convert ${thumbnailPath} -ping -format "%w x %h" info:`

beforeReplace="${orig} x ${new}"

result_string=$(echo $beforeReplace | sed 's/ x /,/g')

echo ${result_string}