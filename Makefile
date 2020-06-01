.PHONY: image up stop clean stop-clean rmi help test unit e2e win-test win-unit win-e2e

default: help

help:
	@echo ----------------------------------------------------------------------
	@echo "                      Available Commands"
	@echo ----------------------------------------------------------------------
	@echo "    > image - Builds the docker image"
	@echo "    > up - Runs the api with docker-compose mode on port 8080."
	@echo "          Make sure to configure variables on .env file"
	@echo "    > stop - Stop the running containers"
	@echo "    > clean - Remove stopped containers"
	@echo "    > stop-clean - Stop and then remove containers"
	@echo "    > rmi - Remove image"
	@echo "         -------   OUTSIDE DOCKER CONTAINER  -------"
	@echo "    > test - Run all tests"
	@echo "    > unit - Run unit tests"
	@echo "    > e2e - Run end-to-end tests"
	@echo "    > win-test - Run all tests on Windows"
	@echo "    > win-unit - Run unit tests on Windows"
	@echo "    > win-e2e - Run end-to-end tests on Windows"

image:
	docker image build -t isabellerosa/starwars-api .

up:
	docker-compose up -d

stop:
	docker container stop $(shell docker container ls -q --filter name=starwars-api --filter name=starwars-mongo-db)

clean:
	docker container rm $(shell docker container ls -a -q --filter name=starwars-api --filter name=starwars-mongo-db) \
		&& docker volume remove $(shell docker volume ls -q --filter name=starwars-vol) \
		&& docker network remove $(shell docker network ls -q --filter name=starwars-network)

stop-clean: stop clean

rmi:
	docker image rm $(shell docker image ls -q --filter reference="isabellerosa/starwars-api")

test:
	./mvnw test

unit:
	./mvnw -Dtest=unit/**/*Tests test

e2e:
	./mvnw -Dtest=e2e/**/*Tests test

win-test:
	mvnw.cmd test

win-unit:
	mvnw.cmd -Dtest=unit/**/*Tests test

win-e2e:
	mvnw.cmd -Dtest=e2e/**/*Tests test