#!/bin/bash

publicKey=$1
privateKey=$2

sudo java -jar -Dserver.port=80 -DEXTERNAL_PUBLIC_KEY=51f70bb07e8fae9bb1f9328532eca6f8 -DEXTERNAL_PRIVATE_KEY=dff9dc72b32645303726a4184a79c23a1e0469ee target/application.jar 