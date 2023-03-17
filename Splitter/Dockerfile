FROM gradle:7.3-jdk17 AS BUILD
WORKDIR /Splitter
COPY . /Splitter
RUN gradle bootJar
FROM eclipse-temurin:19-alpine
WORKDIR /Splitter
COPY --from=BUILD /Splitter/build/libs/*.jar \
                  /Splitter/splitter.jar
EXPOSE 9000
CMD java -jar splitter.jar