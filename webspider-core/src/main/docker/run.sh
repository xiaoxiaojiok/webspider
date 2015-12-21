#!/bin/bash
docker run --name worker -p 22 -p 8888 -p 9090 -p 9999 -d worker:1.0.0
