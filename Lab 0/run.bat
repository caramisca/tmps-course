@echo off
echo =====================================
echo  SOLID Principles Lab 0 - Quick Run
echo =====================================
echo.

echo [1/3] Compiling Java files...
javac -encoding UTF-8 -d classes -cp classes src/main/java/md/utm/tmps/lab0/*.java src/main/java/md/utm/tmps/lab0/srp/*.java src/main/java/md/utm/tmps/lab0/ocp/*.java src/main/java/md/utm/tmps/lab0/lsp/*.java

if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo SUCCESS: Compilation completed!
echo.

echo [2/3] Running SOLID Principles Demonstration...
echo =====================================
java -cp classes md.utm.tmps.lab0.Main
echo.

echo [3/3] Running Test Suite...
echo =====================================
java -cp classes md.utm.tmps.lab0.SimpleTestRunner
echo.

echo =====================================
echo  Lab 0 execution completed!
echo =====================================
pause