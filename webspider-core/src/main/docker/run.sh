#!/bin/bash
if [ $# != 1 ]
then
        echo "Usage: run.sh number"
else
        i=0
        num=$1
        while [ $i -lt $num ]
        do
                ((i++))
                docker run --name worker-$i -p 22 -p 8888 -p 9090 -p 9999 -d worker:1.0.0
        done
fi
