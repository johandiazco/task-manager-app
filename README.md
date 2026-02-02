# 📋 Task Manager API

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen?style=for-the-badge&logo=spring)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?style=for-the-badge&logo=docker)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

Una API REST robusta y escalable para la gestión de tareas, construida con Spring Boot siguiendo las mejores prácticas de arquitectura en capas y principios SOLID.

---

## 🚀 Características

- ✅ **CRUD completo** de tareas
- ✅ **Estados de tareas** (Pendiente/Completada)
- ✅ **Arquitectura en capas** (Controller → Service → Repository)
- ✅ **Base de datos H2** embebida
- ✅ **Dockerizado** para fácil deployment
- ✅ **RESTful API** con códigos HTTP apropiados
- ✅ **Manejo de excepciones** centralizado

---

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.2.2 | Framework backend |
| Spring Data JPA | 3.2.2 | ORM y acceso a datos |
| H2 Database | Runtime | Base de datos en memoria |
| Lombok | Latest | Reducción de boilerplate |
| Maven | 3.9.x | Gestión de dependencias |
| Docker | Latest | Containerización |

---

## 📁 Estructura del Proyecto

```
TaskClaudeAcademy/
├── src/
│   └── main/
│       ├── java/com/TaskClaude/
│       │   ├── controller/      # Capa REST
│       │   │   └── TaskController.java
│       │   ├── service/         # Lógica de negocio
│       │   │   └── TaskService.java
│       │   ├── repository/      # Acceso a datos
│       │   │   └── TaskRepository.java
│       │   ├── model/           # Entidades
│       │   │   ├── Task.java
│       │   │   └── State.java
│       │   └── TaskClaudeAcademyApplication.java
│       └── resources/
│           └── application.properties
├── Dockerfile
├── .dockerignore
├── pom.xml
└── README.md
```

---

## 🔧 Instalación y Ejecución

### **Prerrequisitos**
- Java 17 o superior
- Maven 3.6+
- Docker (opcional)

### **Opción 1: Ejecución local**

1. **Clonar el repositorio:**
```bash
git clone https://github.com/johandiazco/task-manager-app.git
cd task-manager-app
```

2. **Compilar el proyecto:**
```bash
./mvnw clean package -DskipTests
```

3. **Ejecutar la aplicación:**
```bash
./mvnw spring-boot:run
```

4. **Acceder a la API:**
```
http://localhost:8080/api/tasks
```

### **Opción 2: Ejecución con Docker**

1. **Construir la imagen:**
```bash
docker build -t task-manager-app .
```

2. **Ejecutar el contenedor:**
```bash
docker run -d -p 8080:8080 --name task-manager task-manager-app
```

3. **Verificar que está corriendo:**
```bash
docker ps
```

---

## 📡 API Endpoints

### **Base URL:** `http://localhost:8080/api/tasks`

| Método | Endpoint | Descripción | Body |
|--------|----------|-------------|------|
| `GET` | `/` | Listar todas las tareas | - |
| `GET` | `/{id}` | Obtener tarea por ID | - |
| `POST` | `/` | Crear nueva tarea | JSON |
| `PUT` | `/{id}` | Actualizar tarea | JSON |
| `PATCH` | `/{id}/complete` | Marcar como completada | - |
| `DELETE` | `/{id}` | Eliminar tarea | - |

### **Ejemplos de uso:**

#### **Crear una tarea:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Estudiar Spring Boot",
    "description": "Completar proyecto Task Manager"
  }'
```

**Respuesta (201 Created):**
```json
{
  "id": 1,
  "title": "Estudiar Spring Boot",
  "description": "Completar proyecto Task Manager",
  "state": "PENDING",
  "createdAt": "2026-02-02T16:30:00"
}
```

#### **Listar todas las tareas:**
```bash
curl http://localhost:8080/api/tasks
```

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Estudiar Spring Boot",
    "description": "Completar proyecto Task Manager",
    "state": "PENDING",
    "createdAt": "2026-02-02T16:30:00"
  }
]
```

#### **Marcar tarea como completada:**
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/complete
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "title": "Estudiar Spring Boot",
  "description": "Completar proyecto Task Manager",
  "state": "COMPLETED",
  "createdAt": "2026-02-02T16:30:00"
}
```

---

## 🗄️ Base de Datos

### **Acceso a H2 Console:**

1. Acceder a: `http://localhost:8080/h2-console`
2. Configurar conexión:
   - **JDBC URL:** `jdbc:h2:mem:taskdb`
   - **Username:** `sa`
   - **Password:** *(vacío)*
3. Click en **Connect**

### **Esquema de la tabla `tasks`:**

| Columna | Tipo | Restricciones |
|---------|------|---------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| title | VARCHAR(255) | NOT NULL |
| description | VARCHAR(500) | - |
| state | VARCHAR(50) | NOT NULL |
| created_at | TIMESTAMP | NOT NULL, IMMUTABLE |

---

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────┐
│              HTTP REQUEST                    │
└─────────────────┬───────────────────────────┘
                  │
         ┌────────▼────────┐
         │  TaskController │  ← Capa REST
         │  @RestController│
         └────────┬────────┘
                  │
         ┌────────▼────────┐
         │   TaskService   │  ← Lógica de negocio
         │    @Service     │
         └────────┬────────┘
                  │
         ┌────────▼────────┐
         │ TaskRepository  │  ← Acceso a datos
         │   JpaRepository │
         └────────┬────────┘
                  │
         ┌────────▼────────┐
         │   H2 Database   │  ← Persistencia
         └─────────────────┘
```

### **Principios aplicados:**
- ✅ **Separación de responsabilidades**
- ✅ **Inyección de dependencias**
- ✅ **Principios SOLID**
- ✅ **RESTful design**

---

## 🐳 Docker

### **Dockerfile optimizado:**
- **Multi-stage build** para reducir tamaño de imagen
- **JRE 17** en lugar de JDK (imagen más ligera)
- **Puerto 8080** expuesto

### **Comandos útiles:**

```bash
# Ver logs del contenedor
docker logs task-manager

# Detener contenedor
docker stop task-manager

# Eliminar contenedor
docker rm task-manager

# Ver imágenes
docker images
```

---

## 🧪 Testing

_(En desarrollo - próxima iteración)_

Planificado:
- Unit tests con JUnit 5
- Integration tests con MockMvc
- Test de repositorio con H2

---

## 📈 Próximas Mejoras

- [ ] Implementar testing (JUnit + Mockito)
- [ ] Migrar a Arquitectura Hexagonal
- [ ] Añadir autenticación JWT
- [ ] Documentación con Swagger/OpenAPI
- [ ] CI/CD con GitHub Actions
- [ ] Deploy en AWS/Azure
- [ ] Métricas con Actuator

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

---

## 👤 Autor

**Johan Diaz**  
Desarrollador Backend Java | Spring Boot · Docker · REST APIs
Aprendiendo DevOps y Arquitecturas Escalables

- GitHub: https://github.com/johandiazco
- LinkedIn: https://www.linkedin.com/in/johan-adrian-diaz-leal-0364b7207
- Email: johanadriandl@gmail.com

---

## 🙏 Agradecimientos

- Comunidad Spring Boot por la documentación
- Stack Overflow por resolver dudas puntuales

---

<div align="center">

**⭐ Si este proyecto te fue útil, considera darle una estrella ⭐**

Hecho con ☕ por Johan Diaz

</div>
