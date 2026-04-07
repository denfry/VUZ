param()

$ErrorActionPreference = "Stop"
Set-Location -Path $PSScriptRoot

Write-Host "=== Lab8: JavaFX + PostgreSQL ==="
Write-Host "Project: $PSScriptRoot"

if (-not (Test-Path ".\conf.prop")) {
    Write-Error "Файл conf.prop не найден. Создай conf.prop рядом со скриптом."
}

$props = Get-Content ".\conf.prop" -ErrorAction Stop
$urlLine = $props | Where-Object { $_ -like "URL_DB=*" } | Select-Object -First 1
$url = if ($urlLine) { $urlLine.Substring(7) } else { "" }

if ([string]::IsNullOrWhiteSpace($url)) {
    Write-Warning "URL_DB в conf.prop не заполнен."
} else {
    Write-Host "DB URL: $url"
}

$javaOk = $false
try {
    java -version | Out-Null
    $javaOk = $true
} catch {
    Write-Error "Java не найдена в PATH."
}

$mavenOk = $false
try {
    mvn -version | Out-Null
    $mavenOk = $true
} catch {
    Write-Error "Maven не найден в PATH."
}

if ($javaOk -and $mavenOk) {
    Write-Host "Запуск приложения..."
    mvn -q -DskipTests javafx:run
}
