#!/bin/bash
i=0
max_retry=10
while [ 1 ]
do

    count=`curl -Ss 'http://127.0.0.1:8100/pay/cashier/pay/heartbeat' | grep 'OK' | wc -l`
    if [ $count -eq 1 ];then
        exit 0
    fi
    sleep 5s
    i=`expr $i + 1`
    if [ $i -eq $max_retry ];then
        echo "healthCheck failed,exit"
        exit 1
    fi
done