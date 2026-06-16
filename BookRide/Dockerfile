FROM maven:3.9-eclipse-temurin-17-alpine AS builder

WORKDIR /build

COPY pom.xml ./pom.xml
COPY BookRide/pom.xml ./BookRide/pom.xml

ENV MAVEN_OPTS="-Xmx1024m"

COPY Authentication/pom.xml ./Authentication/pom.xml
COPY OfferRide/pom.xml ./OfferRide/pom.xml

RUN mvn dependency:go-offline -f BookRide -B --no-transfer-progress

COPY BookRide/src ./BookRide/src

RUN mvn clean package -pl BookRide -am -Dmaven.test.skip=true -B --no-transfer-progress

FROM eclipse-temurin:17-jre-alpine AS runtime

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /build/BookRide/target/BookRide-0.0.1-SNAPSHOT.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8084

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]