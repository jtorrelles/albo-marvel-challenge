#!/bin/bash

publicKey=$1
privateKey=$2

sudo java -jar -Dserver.port=80 -DEXTERNAL_PUBLIC_KEY=$publicKey -DEXTERNAL_PRIVATE_KEY=$privateKey target/application.jar 