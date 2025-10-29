Write-Host "Building Lab 1 - Creational Design Patterns..." -ForegroundColor Cyan
mvn clean compile

Write-Host "`nRunning demonstration..." -ForegroundColor Cyan
mvn exec:java -Dexec.mainClass="md.utm.tmps.lab1.client.Main"

Write-Host "`nRunning tests..." -ForegroundColor Cyan
mvn test

Write-Host "`nPress any key to continue..." -ForegroundColor Green
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
