# ForoHub 🗣️

API REST para gestión de foros de discusión, desarrollada como parte del programa ONE (Oracle Next Education) de Alura Latam.

## 📋 Descripción

ForoHub permite a los usuarios crear y gestionar tópicos de discusión organizados por cursos, con un sistema de autenticación seguro basado en JWT. Cada tópico puede recibir respuestas de la comunidad, fomentando el intercambio de conocimiento.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Security** — autenticación y autorización
- **JWT (Auth0 4.4.0)** — tokens de acceso
- **Spring Data JPA / Hibernate** — persistencia
- **PostgreSQL 17** — base de datos
- **Flyway** — migraciones de base de datos
- **SpringDoc OpenAPI 2.8.4** — documentación interactiva
- **Lombok** — reducción de boilerplate
- **Maven** — gestión de dependencias

## 🏗️ Arquitectura

El proyecto aplica **Clean Architecture** con separación clara de responsabilidades:
```
src/main/java/com/alura/forohub/
├── domain/
│   ├── model/          # Entidades de negocio
│   ├── port/           # Interfaces de repositorio
│   └── exception/      # Excepciones de dominio
├── application/
│   └── service/        # Lógica de negocio
├── infrastructure/
│   ├── persistence/    # Implementaciones JPA
│   └── security/       # JWT, filtros, configuración
└── presentation/
    ├── controller/     # Endpoints REST
    ├── dto/            # Objetos de transferencia
    └── exception/      # Manejo global de errores
```

## ⚙️ Configuración

### Prerrequisitos
- Java 17+
- PostgreSQL 17
- Maven 3.8+

### Base de datos

Crear la base de datos en PostgreSQL:
```sql
CREATE DATABASE forohub;
```

### Variables de entorno

Copiar el archivo de ejemplo y completar con tus credenciales:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

Editar `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
api.jwt.secret=tu_secret_hex_64_chars
api.jwt.expiration=86400000
```

> Para generar un JWT secret seguro: `openssl rand -hex 32`

Las migraciones Flyway se ejecutan automáticamente al iniciar la aplicación e insertan un usuario administrador por defecto.

## ▶️ Ejecución
```bash
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## 📚 Documentación

Swagger UI disponible en:
http://localhost:8080/swagger-ui/index.html

## 🔄 Flujo de uso con Swagger

1. Acceder a `http://localhost:8080/swagger-ui/index.html`
2. Ejecutar `POST /api/auth/login` con las credenciales del usuario por defecto
3. Copiar el valor del campo `token` de la respuesta
4. Hacer click en el botón **Authorize** 🔒 (esquina superior derecha)
5. Pegar el token en el campo **Value** y confirmar
6. Todos los endpoints protegidos quedarán habilitados para probar

## 🔑 Autenticación

Todas las rutas excepto `/api/auth/login` y `/api/users` requieren token JWT.

**Usuario por defecto:**
- Email: `admin@forohub.com`
- Password: `password`

**Obtener token:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@forohub.com", "password": "password"}'
```

Usar el token en el header de cada request:
```
Authorization: Bearer <token>
```

## 📡 Endpoints

### Autenticación
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/auth/login` | Iniciar sesión | No |

### Usuarios
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/users` | Registrar usuario | No |

### Tópicos
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/topics` | Crear tópico | Sí |
| GET | `/api/topics?page=0&size=10` | Listar tópicos paginados | Sí |
| GET | `/api/topics/{id}` | Obtener tópico por ID | Sí |
| PUT | `/api/topics/{id}` | Actualizar tópico | Sí |
| DELETE | `/api/topics/{id}` | Eliminar tópico | Sí |

### Respuestas
| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/responses` | Crear respuesta | Sí |
| GET | `/api/responses/topic/{topicId}` | Listar respuestas de un tópico | Sí |

## 🧪 Tests
```bash
mvn test
```

7 tests en total:
- **3 tests unitarios** — `TopicService` con Mockito
- **3 tests de integración** — `AuthController` con MockMvc
- **1 test de contexto** — carga del contexto de Spring

## 📄 Licencia

Proyecto desarrollado con fines educativos — Alura ONE.

---
