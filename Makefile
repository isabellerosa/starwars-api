.PHONY: build dev prod stop clean stop-clean help

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
	@echo "    > test - Run all tests"
	@echo "    > unit - Run unit tests"
	@echo "    > e2e - Run end-to-end tests"

image:
	docker image build -t isabellerosa/starwars-api .

up:
	docker-compose up -d

stop:
	docker container stop $(shell docker container ls -q --filter name=starwars-api_starwars)

clean:
	docker container rm $(shell docker container ls -a -q --filter name=starwars-api_starwars) \
		&& docker volume remove starwars-api_starwars-vol \
		&& docker network remove starwars-api_starwars-network

stop-clean: stop clean

rmi:
	docker image rm $(shell docker image ls -q --filter reference="isabellerosa/starwars-api")

test:
	./mvnw test

unit:
	./mvnw -Dtest=unit/**/*Tests test

e2e:
	./mvnw -Dtest=e2e/**/*Tests test