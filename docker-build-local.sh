#!/bin bash
DOCKER_BUILDKIT=1 docker build -f DockerFileLocal -t city-list-api .

#https://spring.io/guides/topicals/spring-boot-docker/