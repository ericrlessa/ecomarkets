#!/bin/bash

SDIR="$(cd "$(dirname "${BASH_SOURCE[0]}" )" && pwd)"
source $SDIR/utils.sh
DIR=$(dirname $SDIR)

info "Building mvn modules"
cd $DIR
mvn -fn -DskipTests install

info "Building npm modules"
cd app
npm install
npm run build
cd ..

export VERSION=$(cat VERSION)
info "Building docker images for version $VERSION"
docker system prune -f

export DOCKER_XARGS="--no-cache --progress=plain --build-arg VERSION=$VERSION"

info "Building core docker images"
docker build -f core/build.Containerfile $DOCKER_XARGS -t caravanacloud/ecomarkets-core-build:$VERSION .
docker build -f core/Containerfile $DOCKER_XARGS -t caravanacloud/ecomarkets-core-runtime:$VERSION .

info "Building web docker image"
docker build -f web/Containerfile $DOCKER_XARGS -t caravanacloud/ecomarkets-web:$VERSION .

info "Building api docker image"
docker build -f api/Containerfile $DOCKER_XARGS -t caravanacloud/ecomarkets-api:$VERSION .

info "Building vdn docker image"
docker build -f vdn/Containerfile $DOCKER_XARGS -t caravanacloud/ecomarkets-vdn:$VERSION .

info "Building app docker image"
docker build -f app/Containerfile $DOCKER_XARGS -t caravanacloud/ecomarkets-app:$VERSION .

info "Checking docker login"
docker login --username="$DOCKER_USERNAME" --password="$DOCKER_PASSWORD"

info "Pushing core docker images"
docker push caravanacloud/ecomarkets-core-build:$VERSION
docker push caravanacloud/ecomarkets-core-runtime:$VERSION

info "Pushing web docker image"
docker push caravanacloud/ecomarkets-web:$VERSION

info "Pushing api docker image"
docker push caravanacloud/ecomarkets-api:$VERSION

info "Pushing vdn docker image"
docker push caravanacloud/ecomarkets-vdn:$VERSION

info "Pushing app docker image"
docker push caravanacloud/ecomarkets-app:$VERSION

info done
