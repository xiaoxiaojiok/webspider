#!/bin/bash

RESULT=$(/sbin/ip -oneline route get 192.168.1.1)
HOST_IP=$(echo ${RESULT#*src}|cut -d " " -f1)

docker run --name admin -e HOST_IP=$HOST_IP -p 22 -p 8888:8888 -p 9090 -p 9999:9999 -d admin:1.0.0
