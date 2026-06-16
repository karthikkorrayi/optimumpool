FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

COPY pom.xml ./pom.xml
COPY OfferRide/pom.xml ./OfferRide/pom.xml

RUN mvn dependency:go-offline -pl OfferRide -am -B --no-transfer-progress

COPY OfferRide/src ./OfferRide/src

RUN mvn clean package -pl OfferRide -am -DskipTests -B --no-transfer-progress

FROM eclipse-temurin:17-jre-alpine AS runtime

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

COPY --from=builder /build/OfferRide/target/OfferRide-0.0.1-SNAPSHOT.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8086

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]