#!/usr/bin/env bash

if ! [[ $(docker ps -q -f name=localstack) ]]; then
  echo "WARNING: The localstack Docker container is not running. Please, start it first."
  exit 1
fi

echo
echo "Initializing LocalStack"
echo "======================="

echo
echo "Installing jq"
echo "-------------"
docker exec -t localstack apt-get -y install jq

AWS_LOCALSTACK_URL="http://localhost.localstack.cloud:4566"

echo
echo
echo "Creating S3 bucket"
echo "------------------"

docker exec -t localstack aws --endpoint-url=http://localhost:4566 s3 mb s3://com.narminya.streams.images

echo
echo "Creating secrets in secretsmanager"
echo "----------------------------------"

docker exec -t localstack aws --endpoint-url=http://localhost:4566 secretsmanager create-secret --name /secrets/omdbApi --secret-string "{\"apiKey\"}"

echo
echo "----------------------------------------"
echo "           AWS_LOCALSTACK_URL=$AWS_LOCALSTACK_URL"
echo "----------------------------------------"

echo
echo "LocalStack initialized successfully"
echo "==================================="
echo