# RateLimiter

> Проект для обучения по написанию тестов к Spring Boot
приложению.

Сервис для ограничения частоты API-запросов по ключу клиента.\

## 📂 Структура проекта

```bash
rate-limiter/
├── app/ # главный Spring Boot-модуль
├── domain/ # доменная модель и интерфейсы
├── persistence/ # JPA-сущности и репозитории
├── api/ # REST-контроллеры и DTO
├── gradle/ # Gradle Wrapper
├── gradlew* # Unix-скрипт для сборки
├── gradlew.bat* # Windows-скрипт для сборки
├── docker-compose.yml # сборка PostgreSQL для профиля docker
```

| Слой            | Ответственность                               | Примеры                            |
|-----------------|-----------------------------------------------|------------------------------------|
| `domain/`       | Чистая бизнес-логика                          | интерфейсы, record-классы, сервисы |
| `application/`  | Оркестрация use case-ов (фасады, координация) | имплементация `RateLimiterService` |
| `persistence/`  | Работа с БД, JPA-репозитории                  | `ClientAppRepository`, `Entity`    |
| `api/`          | REST-интерфейс                                | контроллеры, DTO                   |

## 🛠 Технологии

- **Java 21** (LTS)
- **Spring Boot 3.2.5**
- **Gradle 8.13** (Wrapper)
- **Spring Data JPA**, **Spring MVC**
- **H2 Database** (in-memory, профиль по умолчанию)
- **PostgreSQL** + **Docker Compose** (профиль `docker`)

## ⚙️ Системные требования

- Установленный **JDK 21**
- **Docker 20.10+** (для профиля `docker`)
- Порт **8080** свободен для HTTP
- Порт **5432** свободен для PostgreSQL (при запуске Docker-сервиса)

## 🚀 Что делает сервис

1. **Регистрирует** клиентов (ClientApp) с API-ключами.
2. Хранит **правила** ограничений (RateLimitRule): макс. запросов за окно времени.
3. При **POST /api/check** с заголовком `X-API-Key` проверяет, не превысил ли клиент лимит:
    - Если **OK**, возвращает `200 OK` с `{ allowed: true }`.
    - Если лимит исчерпан, возвращает `429 TOO MANY REQUESTS` с `{ allowed: false, reason: "Limit exceeded" }`.
4. Логирует каждый запрос (RequestLog) и считает последние N запросов за окно.

## ▶️ Запуск

### 1. Локально (H2, профиль по-умолчанию)

```bash
# Unix/macOS/Linux
./gradlew clean :app:bootRun
```

```shell
# Windows (PowerShell или cmd)
.\gradlew.bat clean :app:bootRun
```

* Приложение запустится на http://localhost:8080
* H2-консоль: http://localhost:8080/h2-console
  * JDBC URL: `jdbc:h2:mem:ratelimiter` 
  * User: `sa`, пароль пустой

### 2. Через Docker Compose (PostgreSQL, профиль docker)

```bash
# В корне проекта
docker-compose up -d
```

```bash
# Unix/macOS/Linux
./gradlew :app:bootRun --args='--spring.profiles.active=docker'
```

```shell
# Windows
.\gradlew.bat :app:bootRun --args="--spring.profiles.active=docker"
```

* Контейнер PostgreSQL доступен на `localhost:5432`
* Переменные (по-умолчанию):
  * DB_HOST=`localhost`
  * DB_PORT=`5432`
  * DB_NAME=`ratelimiter`
  * DB_USER=`postgres`
  * DB_PASSWORD=`postgres`

## 📞 Вызов API

* Регистрация нового пользователя

```bash
curl -X POST "http://localhost:8080/api/register" \
  -H "Content-Type: application/json" \
  -d '{"name": "MyAwesomeClient"}'
```

* Запрос анализа текста

```bash
curl -X POST "http://localhost:8080/api/words" \
  -H "Content-Type: application/json" \
  -H "X-API-Key: e8884ad9-b7d3-41a1-a4cc-562805621517" \
  -d '{"text": "Some Text"}'
```

> Заменить `X-API-Key` значение на полученное после регистрации `/api/register`

## 🔗 Полезные команды

* `./gradlew build` — собрать все модули
* `./gradlew :persistence:test` — запустить только тесты репозитория
* `./gradlew :app:bootRun` — запустить приложение
* `docker-compose down` — остановить контейнеры
* `./gradlew :app:bootRun --args='--debug'` — запуск приложения в режиме отладки


