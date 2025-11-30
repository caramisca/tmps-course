#!/usr/bin/env pwsh

Write-Host "╔════════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║     Lab 3 - Behavioral Design Patterns                    ║" -ForegroundColor Cyan
Write-Host "║     Computer Components Store System                      ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Check if Maven is installed
if (-not (Get-Command mvn -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Error: Maven is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Maven and try again." -ForegroundColor Yellow
    exit 1
}

Write-Host "🔨 Compiling project..." -ForegroundColor Yellow
mvn clean compile

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "❌ Compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "✅ Compilation successful!" -ForegroundColor Green
Write-Host ""
Write-Host "🧪 Running tests..." -ForegroundColor Yellow
mvn test

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "❌ Tests failed!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "✅ All tests passed!" -ForegroundColor Green
Write-Host ""
Write-Host "▶️  Running main demonstration..." -ForegroundColor Yellow
Write-Host ""
Write-Host "════════════════════════════════════════════════════════════" -ForegroundColor Cyan

mvn exec:java -Dexec.mainClass="md.utm.tmps.lab3.client.Main" -q

Write-Host ""
Write-Host "════════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""
Write-Host "✅ Execution completed successfully!" -ForegroundColor Green
