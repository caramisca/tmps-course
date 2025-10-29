@echo off
echo Building Lab 1 - Creational Design Patterns...
call mvn clean compile

echo.
echo Running demonstration...
call mvn exec:java -Dexec.mainClass="md.utm.tmps.lab1.client.Main"

echo.
echo Running tests...
call mvn test

pause
