#!/bin/bash

RESULT=$(/sbin/ip -oneline route get 192.168.1.1)
HOST_IP=$(echo ${RESULT#*src}|cut -d " " -f1)

if [ $# != 3 ]
then
        echo "Usage: run.sh total numberStart jettyPortStart"
else
        num=$2
        jettyPort=$3
        for((i=0;i<$1;i++))
        do
                docker run --name worker-$num -e HOST_IP=$HOST_IP -e JETTY_PORT=$jettyPort -p 22 -p 8888 -p 9090 -p $jettyPort:9999 -d worker:1.0.0
                ((num++))
                ((jettyPort++))
        done
fi
