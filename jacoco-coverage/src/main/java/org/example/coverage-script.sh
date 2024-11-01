#!/bin/bash

# Define paths (make sure paths with spaces are in double quotes)
JACOCO_AGENT_PATH="/home/fundacion/Git-University-Projects/Development 6/jacoco-0.8.12/lib/jacocoagent.jar"
APPLICATION_JAR_PATH="/home/fundacion/Git-University-Projects/Development 6/calculator/out/artifacts/calculator_jar/calculator.jar"  # Replace '.jar' with the actual JAR file name
COVERAGE_FILE_PATH="jacoco.exec"

# Start the application with JaCoCo agent
java -javaagent:"$JACOCO_AGENT_PATH"=destfile="$COVERAGE_FILE_PATH" -jar "$APPLICATION_JAR_PATH" &
APPLICATION_PID=$!

# Wait for the application to initialize
sleep 10

# Run tests with Gradle (adjust to the correct Gradle command for your setup)
cd "/home/fundacion/Git-University-Projects/Development 6/calculator"  # Ensure correct directory
./gradlew test

# Terminate the application
kill $APPLICATION_PID

# Optional: add commands here to generate the coverage report using `jacococli.jar` if needed

