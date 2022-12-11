#
# Build stage
#
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /home
COPY src /home/src
COPY target/ /home/target
COPY pom.xml /home
RUN \
   sed -i -e 's?server.patient=.*$?server.patient=http://sprint1patient:8081?'\
          -e 's?server.note=.*$?server.note=http://sprint2note:8082?' \
          -e 's?server.risk=.*$?server.risk=http://sprint3risk:8083?' \
                /home/src/main/resources/application.properties
RUN mvn clean -B package -D maven.test.skip=true


#
# Package stage
#
FROM openjdk:17-jdk-slim-buster
WORKDIR /home
COPY --from=build /home/target/pageWeb-0.0.1-SNAPSHOT.jar /home
COPY entrypoint.sh /
EXPOSE 8084
ENTRYPOINT ["/entrypoint.sh"]