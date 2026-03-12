#!/bin/sh

# Create necessary directories
mkdir -p /app/data /app/slave /app/attachments /app/csv

# Copy CSV files if they exist
if [ -d "/app/csv" ]; then
    echo "CSV files directory exists"
fi

# Set JVM options for cloud deployment
export JAVA_OPTS="-Xmx384m -Xms256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# Start the application
exec java $JAVA_OPTS -Dserver.port=${PORT:-8080} -Dspring.profiles.active=production -jar app.jar
