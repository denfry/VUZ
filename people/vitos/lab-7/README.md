# Лабораторная работа 7 (JDBC), вариант 11

Проект содержит:
- окно авторизации к PostgreSQL;
- главное окно приложения;
- окно работы с БД (`SELECT VERSION()`, просмотр `bot_users`);
- создание и заполнение демонстрационных таблиц:
  `bot_users`, `trading_strategies`, `orders`.

## Конфигурация

Параметры подключения находятся в `conf.prop`:

```properties
URL_DB=jdbc:postgresql://localhost:5433/lab7_variant11
User=trading_bot
Pwd=devpassword
```

При необходимости измени их под свою БД.

## Запуск из IDE

Открой Maven-проект и запусти `com.lab7.MainApp`.

## Запуск через Maven

```bash
mvn javafx:run
```

## Сборка JAR

```bash
mvn clean package
```
