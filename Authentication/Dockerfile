# ─────────────────────────────────────────────
# STAGE 1: BUILD
# Use the full JDK + Maven image to compile
# eclipse-temurin is the official OpenJDK distribution — well-maintained, free
# ─────────────────────────────────────────────
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
# Set a working directory inside the build container
WORKDIR /build

# Copy the PARENT pom.xml first (one level up from Authentication/)
# Why: Maven needs the parent pom to resolve the module structure and shared deps
COPY pom.xml ./pom.xml

# Copy this service's pom.xml
COPY Authentication/pom.xml ./Authentication/pom.xml

COPY OfferRide/pom.xml ./OfferRide/pom.xml
COPY BookRide/pom.xml ./BookRide/pom.xml

# Download dependencies BEFORE copying source code.
# Why: Docker caches each layer. If pom.xml hasn't changed,
# Docker reuses the cached dependency layer and skips the download.
# This makes rebuilds after code-only changes go from 3 minutes to 10 seconds.
# RUN apk add --no-cache maven
RUN mvn dependency:go-offline -f Authentication -B --no-transfer-progress

# NOW copy the source code
COPY Authentication/src ./Authentication/src

# Build the jar, skip tests (tests run in CI, not in Docker build)
# -pl Authentication = build only this module
# -am = also build parent/dependencies it needs
RUN mvn clean package -pl Authentication -am -Dmaven.test.skip=true -B --no-transfer-progress

# ─────────────────────────────────────────────
# STAGE 2: RUNTIME
# Use only the JRE — no compiler, no Maven, no source code
# This is the image that actually runs in production
# ─────────────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine AS runtime

# Create a non-root user to run the app.
# Running as root inside a container is a security risk.
# If the container is ever compromised, root access = game over.
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Copy ONLY the compiled jar from the builder stage.
# Nothing else crosses into this image.
COPY --from=builder /build/Authentication/target/Authentication-0.0.1-SNAPSHOT.jar app.jar

# Change ownership to our non-root user
RUN chown appuser:appgroup app.jar

# Switch to non-root user
USER appuser

# Document which port this service uses (doesn't actually publish it — that's docker-compose's job)
EXPOSE 8020

# How to start the app.
# -XX:+UseContainerSupport  → tells JVM to respect Docker's CPU/memory limits
# -XX:MaxRAMPercentage=75.0 → use max 75% of container's allocated RAM for JVM heap
# These two flags prevent the JVM from grabbing all host memory, which crashes containers
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]