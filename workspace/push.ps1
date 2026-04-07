# Скрипт для загрузки проекта на GitHub
# Использование: .\push.ps1

Write-Host "=== Загрузка проекта на GitHub ===" -ForegroundColor Green
Write-Host ""

# Запрашиваем URL репозитория
$repoUrl = Read-Host "Введите URL вашего GitHub репозитория (например: https://github.com/username/repo-name.git)"

if ([string]::IsNullOrWhiteSpace($repoUrl)) {
    Write-Host "Ошибка: URL репозитория не может быть пустым!" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Добавляю remote origin..." -ForegroundColor Yellow
git remote add origin $repoUrl 2>$null
if ($LASTEXITCODE -ne 0) {
    Write-Host "Remote уже существует, обновляю..." -ForegroundColor Yellow
    git remote set-url origin $repoUrl
}

Write-Host "Отправляю код на GitHub..." -ForegroundColor Yellow
git push -u origin main

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✓ Проект успешно загружен на GitHub!" -ForegroundColor Green
    Write-Host "Откройте репозиторий: $repoUrl" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "✗ Ошибка при загрузке. Проверьте:" -ForegroundColor Red
    Write-Host "  1. Правильность URL репозитория" -ForegroundColor Red
    Write-Host "  2. Что репозиторий создан на GitHub" -ForegroundColor Red
    Write-Host "  3. Что у вас есть права на запись" -ForegroundColor Red
}

