build:
	docker run --rm -v $(pwd):/home/gradle gradle:jdk8-alpine gradle shadowJar

image: build
	docker build -t bitics-sink .
