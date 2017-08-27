FROM openjdk:8u131-jre-alpine

COPY build/libs/sink-1.0-SNAPSHOT-all.jar /opt/bittics/

CMD java -jar /opt/bittics/sink-1.0-SNAPSHOT-all.jar
