#!/bin/sh
image_name=email-sender-microservice-image

echo "Build image with name: $image_name"

docker image build -t $image_name .

retVal=$?
if [ $retVal -eq 0 ]; then
  echo "Image with name $image_name has been successful built"

  echo "Starting local docker compose file"

  docker-compose -f docker-compose.local.yml up

  if [ $? -nq 0 ]; then
      echo "Cannot build docker container"
      exit 1
  exit 0
  fi

  else
    echo "Image building has been failed. Stopped"
    exit 1
fi
