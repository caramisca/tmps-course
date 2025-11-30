@echo off
setlocal

echo ╔════════════════════════════════════════════════════════════╗
echo ║     Lab 3 - Behavioral Design Patterns                    ║
echo ║     Computer Components Store System                      ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Check if Maven is installed
where mvn >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Error: Maven is not installed or not in PATH
    echo Please install Maven and try again.
    exit /b 1
)

echo 🔨 Compiling project...
call mvn clean compile

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Compilation failed!
    exit /b 1
)

echo.
echo ✅ Compilation successful!
echo.
echo 🧪 Running tests...
call mvn test

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ❌ Tests failed!
    exit /b 1
)

echo.
echo ✅ All tests passed!
echo.
echo ▶️  Running main demonstration...
echo.
echo ════════════════════════════════════════════════════════════

call mvn exec:java -Dexec.mainClass="md.utm.tmps.lab3.client.Main" -q

echo.
echo ════════════════════════════════════════════════════════════
echo.
echo ✅ Execution completed successfully!

endlocal
