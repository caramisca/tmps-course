# SOLID Principles Lab 0 - PowerShell Runner
Write-Host "=====================================" -ForegroundColor Green
Write-Host " SOLID Principles Lab 0 - Quick Run" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Write-Host ""

Write-Host "[1/3] Compiling Java files..." -ForegroundColor Yellow
$compileResult = & javac -encoding UTF-8 -d classes -cp classes src/main/java/md/utm/tmps/lab0/*.java src/main/java/md/utm/tmps/lab0/srp/*.java src/main/java/md/utm/tmps/lab0/ocp/*.java src/main/java/md/utm/tmps/lab0/lsp/*.java 2>&1

if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Compilation failed!" -ForegroundColor Red
    Write-Host $compileResult -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host "SUCCESS: Compilation completed!" -ForegroundColor Green
Write-Host ""

Write-Host "[2/3] Running SOLID Principles Demonstration..." -ForegroundColor Yellow
Write-Host "=====================================" -ForegroundColor Cyan
& java -cp classes md.utm.tmps.lab0.Main
Write-Host ""

Write-Host "[3/3] Running Test Suite..." -ForegroundColor Yellow
Write-Host "=====================================" -ForegroundColor Cyan
& java -cp classes md.utm.tmps.lab0.SimpleTestRunner
Write-Host ""

Write-Host "=====================================" -ForegroundColor Green
Write-Host " Lab 0 execution completed!" -ForegroundColor Green
Write-Host "=====================================" -ForegroundColor Green
Read-Host "Press Enter to exit"