# Employee Managment System(EMS) Backend (Spring Boot + MySQL)

## Описание проекта

Этот проект представляет собой демо-приложение, использующее Spring Boot для создания бэкенда и React.js для фронтенда. Бэкенд работает с базой данных MySQL и предоставляет REST API для взаимодействия с фронтендом.
>Ссылка на [FrontEnd](https://github.com/DANTECK-dev/ems-react-spring)

## Стек технологий

- **Java 23**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **MySQL**
- **Spring Boot DevTools** (для автоматической перезагрузки в процессе разработки)
- **Mockito** (для юнит-тестирования)
- **JSONPath** (для тестирования JSON-ответов)

## Требования

- **JDK 23**
- **MySQL 8.0+**

## Настройка и запуск проекта

### 1. Настройка MySQL

1. Убедитесь, что MySQL установлен и работает.
2. Создайте базу данных для проекта:

   ```sql
   CREATE DATABASE ems;
   ```

3. Обновите настройки подключения к базе данных в файле `application.properties` (или `application.yml`), который находится в папке `src/main/resources`.

   Пример конфигурации для `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ems?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
   ```

   Замените `your_password` на пароль для вашего пользователя MySQL.

### 2. Установка зависимостей

Проект использует **Maven** для управления зависимостями. Чтобы установить все необходимые зависимости, выполните команду:

```bash
mvn clean install
```

### 3. Запуск проекта

Для запуска приложения используйте команду:

```bash
mvn spring-boot:run
```

Это запустит сервер на порту `8080` (по умолчанию).

### 4. Пример работы с REST API

Приложение предоставляет несколько эндпоинтов для работы с сущностями (например, сотрудников).

- **POST** `/api/v1/employees` - создание нового сотрудника.
- **GET** `/api/v1/employees` - получение списка сотрудников.
- **GET** `/api/v1/employees/{id}` - получение информации о сотруднике по ID.
- **PUT** `/api/v1/employees/{id}` - обновление данных сотрудника.
- **DELETE** `/api/v1/employees/{id}` - удаление сотрудника.

Пример запроса для создания нового сотрудника:

```text
POST /api/v1/employees
Content-Type: application/json
```
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com"
}
```

### 5. Тестирование

Для тестирования используются **JUnit 5** и **Mockito**. Тесты можно запускать через Maven:

```bash
mvn test
```

Тесты находятся в директории `src/test/java`.

## Структура проекта

- **`src/main/java`** — исходный код приложения.
- **`src/main/resources`** — ресурсы, такие как `application.properties`.
- **`src/test/java`** — тесты приложения.
- **`pom.xml`** — файл для управления зависимостями с использованием Maven.

## Примечания

- Для удобства разработки используйте **Spring Boot DevTools** для автоматической перезагрузки при изменениях в коде.
- Проект настроен на использование MySQL, однако для работы с другими базами данных нужно будет изменить настройки подключения в `application.properties`.
- Все миграции базы данных управляются через **JPA** (Hibernate), и схема базы данных будет автоматически обновляться с каждым запуском приложения.

## Лицензия

Этот проект распространяется под [лицензией MIT](LICENSE).
