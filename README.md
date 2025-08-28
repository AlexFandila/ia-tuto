# AI Journal Application

Una aplicación Spring Boot con arquitectura hexagonal para gestionar entradas de diario y análisis con IA.

## 🚀 Tecnologías

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA** 
- **H2 Database** (en memoria)
- **Spring AI** con integración OpenAI
- **Lombok**
- **Arquitectura Hexagonal**

## 📋 Requisitos

- Java 17+
- Maven 3.6+
- Cuenta de OpenAI con API Key

## ⚙️ Configuración

### 1. Clonar el repositorio
```bash
git clone <repository-url>
cd ia
```

### 2. Configurar OpenAI API Key

Tienes 3 opciones para configurar tu API Key:

#### Opción A: Variable de entorno (Recomendada)
```bash
export OPENAI_API_KEY="tu-api-key-aqui"
mvn spring-boot:run
```

#### Opción B: Profile local (Desarrollo)
```bash
# Crear archivo de configuración local (ya ignorado por git)
cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties

# Editar y agregar tu API key
# spring.ai.openai.api-key=tu-api-key-aqui

# Ejecutar con profile local
mvn spring-boot:run -Dspring.profiles.active=local
```

#### Opción C: Parámetro de línea de comandos
```bash
mvn spring-boot:run -Dspring.ai.openai.api-key="tu-api-key-aqui"
```

### 3. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## 🗄️ Base de Datos

La aplicación usa H2 en memoria con datos de prueba precargados.

### Acceder a H2 Console
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (vacío)

### Datos de Prueba
- 3 usuarios precargados
- 9 entradas de diario de ejemplo

## 🌐 API Endpoints

### Usuarios
- `GET /api/users` - Listar usuarios
- `POST /api/users` - Crear usuario
- `GET /api/users/{id}` - Obtener usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Entradas de Diario
- `GET /api/journal-entries` - Listar entradas
- `POST /api/journal-entries` - Crear entrada
- `GET /api/journal-entries/{id}` - Obtener entrada
- `GET /api/journal-entries/user/{userId}` - Entradas por usuario
- `PUT /api/journal-entries/{id}` - Actualizar entrada
- `DELETE /api/journal-entries/{id}` - Eliminar entrada

### Análisis IA
- `GET /api/ai/analyze/journal-entry/{id}` - Analizar entrada
- `GET /api/ai/insights/user/{userId}` - Insights del usuario
- `POST /api/ai/ask` - Hacer pregunta sobre journals

## 🏗️ Arquitectura

El proyecto sigue los principios de **Arquitectura Hexagonal**:

```
domain/                     # Lógica de negocio pura
├── model/                  # Entidades del dominio
└── port/                   # Interfaces (puertos)
    ├── in/                 # Casos de uso
    └── out/                # Contratos externos

application/                # Servicios de aplicación
└── service/                # Implementación de casos de uso

infrastructure/             # Adaptadores
├── adapter/
│   ├── in/web/            # Controllers REST
│   └── out/               # Adaptadores externos
│       ├── persistence/   # Persistencia JPA
│       └── ai/           # Servicio OpenAI
└── configuration/         # Configuración Spring
```

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Ejecutar con coverage
mvn clean test jacoco:report
```

## 🚀 Deployment

### Docker (Opcional)
```bash
# Build
mvn clean package
docker build -t ai-journal .

# Run
docker run -p 8080:8080 -e OPENAI_API_KEY="tu-api-key" ai-journal
```

## 🔒 Seguridad

- ✅ **API Keys** nunca committeadas al repositorio
- ✅ **Variables de entorno** para configuración sensible
- ✅ **Profiles** específicos para diferentes entornos
- ✅ **.gitignore** configurado para archivos sensibles

## 📝 Notas de Desarrollo

1. **No commitear** archivos `application-local.properties`
2. **Usar variables de entorno** en producción
3. **Revisar logs** para debugging (nivel DEBUG en desarrollo)

## 🤝 Contribuir

1. Fork el proyecto
2. Crear branch feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo `LICENSE` para detalles.