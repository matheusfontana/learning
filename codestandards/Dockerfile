FROM eclipse-temurin:21-jre-alpine

# Create non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Install curl for health checks
RUN apk add --no-cache curl

WORKDIR /app

# Copy jar file
ARG JAR_FILE=build/libs/codestandards-local.jar
COPY ${JAR_FILE} codestandards.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Expose port
EXPOSE 8080

# Optimized JVM parameters for containers
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=87.5", \
    "-XX:+UseG1GC", \
    "-XX:+UseStringDeduplication", \
    "-XX:+OptimizeStringConcat", \
    "-Djava.security.egd=file:/dev/./urandom", \
#    "-Dspring.profiles.active=docker", \
    "-jar", "codestandards.jar"]

# Health check
HEALTHCHECK --start-period=30s --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

#FOR PROD
# JVM monitoring
#-XX:+FlightRecorder \
#-XX:StartFlightRecording=duration=60s,filename=app.jfr \
# Security
#-Djava.awt.headless=true \
#-Dfile.encoding=UTF-8