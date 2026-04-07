# Инструкция по загрузке на GitHub

## Шаг 1: Создайте репозиторий на GitHub

1. Перейдите на https://github.com
2. Нажмите кнопку "+" в правом верхнем углу
3. Выберите "New repository"
4. Введите название репозитория (например: `web-labs` или `university-labs`)
5. Выберите "Public" или "Private"
6. НЕ добавляйте README, .gitignore или лицензию (они уже есть)
7. Нажмите "Create repository"

## Шаг 2: Выполните команды для пуша

После создания репозитория GitHub покажет вам команды. Выполните их в терминале в папке проекта.

Или используйте эти команды (замените YOUR_USERNAME и REPO_NAME на ваши данные):

```bash
git remote add origin https://github.com/YOUR_USERNAME/REPO_NAME.git
git branch -M main
git push -u origin main
```

## Альтернативный способ (через SSH)

Если у вас настроен SSH ключ:

```bash
git remote add origin git@github.com:YOUR_USERNAME/REPO_NAME.git
git branch -M main
git push -u origin main
```

