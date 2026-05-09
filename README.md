# Centro de Salud Universitario — JyAA

Sistema web fullstack para la gestión de campañas de salud comunitaria, desarrollado para la materia **Java y Aplicaciones Avanzadas (JyAA)** de la UNLP.

---

## Tecnologías

**Backend**
- Java 17 + Jakarta EE
- Jersey 3.1.3 (JAX-RS / REST)
- Hibernate 6.4.1 + JPA 3.1
- Weld 5.0.1 (CDI)
- JWT (JJWT 0.11.5) + JBCrypt
- Swagger / OpenAPI 2.2.10
- MySQL 8
- Apache Tomcat 11
- Maven

**Frontend**
- Angular 20 (Standalone Components)
- Bootstrap 5.3 + Bootstrap Icons
- Leaflet (mapas interactivos)
- TypeScript 5.8 (strict mode)

**Infraestructura**
- Docker (build multi-stage: Node → Maven → Tomcat)
- GitHub Actions (CI/CD)

---

## Estructura del proyecto

```
centro-salud-universitario/
├── backend/          # API REST Java/Jakarta EE
│   ├── src/
│   └── pom.xml
├── frontend/         # SPA Angular 20
│   ├── src/
│   └── angular.json
├── docs/             # Diagramas UML y documentación
├── Dockerfile        # Build multi-stage (Node + Maven → Tomcat)
└── .github/
    └── workflows/
        └── docker.yml   # GitHub Actions CI/CD
```

---

## Configuración previa — Base de datos

Antes de levantar el proyecto, editá el archivo:

```
backend/src/main/resources/META-INF/persistence.xml
```

Reemplazá los placeholders con tus credenciales reales:

| Placeholder      | Descripción                                          |
|------------------|------------------------------------------------------|
| `{DB_HOST}`      | Host donde corre MySQL (ej: `localhost` o IP del servidor) |
| `{YOUR_DB_NAME}` | Nombre de la base de datos MySQL                     |
| `{YOUR_DB_USER}` | Usuario de MySQL                                     |
| `{YOUR_DB_PASSWORD}` | Contraseña de MySQL                              |

Hay dos unidades de persistencia en el archivo (`production` y `development`). Configurá ambas con tus credenciales.

---

## Cómo ejecutar

### Modo producción — Docker (recomendado)

El Dockerfile realiza un build **completamente automatizado** en tres etapas:
1. Compila el frontend Angular con Node.js
2. Copia el build de Angular al webapp y compila el backend con Maven generando un WAR
3. Despliega el WAR en Tomcat como `ROOT.war`

```bash
# 1. Clonar el repositorio
git clone https://github.com/TU_USUARIO/centro-salud-universitario.git
cd centro-salud-universitario

# 2. Configurar credenciales en persistence.xml (ver sección anterior)

# 3. Construir imagen y levantar
docker build -t centro-salud .
docker run -p 8080:8080 centro-salud
```

Aplicación disponible en: `http://localhost:8080`

En este modo, Angular y la API REST conviven en el mismo servidor Tomcat:
- Frontend → `http://localhost:8080/`
- API REST → `http://localhost:8080/rest/*`
- Swagger UI → `http://localhost:8080/swagger-ui/`

---

### Modo desarrollo — Frontend y backend por separado

Para desarrollar con hot-reload en el frontend, levantá el backend con Docker y el frontend con `ng serve`:

```bash
# Terminal 1 — backend con Docker en puerto 8080
docker build -t centro-salud .
docker run -p 8080:8080 centro-salud

# Terminal 2 — frontend con servidor de desarrollo Angular
cd frontend
npm install
npm start
```

Frontend disponible en: `http://localhost:4200`

El servidor de desarrollo de Angular incluye un **proxy** (`src/proxy.dev.json`) que redirige automáticamente todas las llamadas a `/rest/*` hacia el backend en `http://localhost:8080`. No hace falta ninguna configuración adicional.

---

### Actualizar el frontend sin reconstruir Docker

Si modificás el frontend y querés ver los cambios en producción sin esperar el build completo de Docker, podés compilar el Angular manualmente y reemplazar los archivos del webapp:

```bash
# 1. Compilar el frontend
cd frontend
npm run build
# Output generado en: frontend/dist/JyAA/browser/

# 2. Reemplazar archivos en el webapp del backend
#    (eliminar los .js, .css e index.html viejos y copiar los nuevos)
```

Después de este paso, reconstruí la imagen Docker normalmente. El Dockerfile limpia los archivos viejos automáticamente antes de empaquetar el WAR.

---

## Documentación

La carpeta `docs/` contiene:
- Diagrama UML del modelo de dominio (`uml_entrega_2.pdf`)
- Modelo StarUML editable (`.mdj`)

La API REST está documentada con Swagger UI en: `http://localhost:8080/swagger-ui/`

---

## Funcionalidades principales

- Autenticación con JWT y sesión por cookies HTTP-only
- Gestión de usuarios, roles y permisos
- Gestión de campañas, jornadas y encuestadores
- Visualización geográfica de zonas y barrios (Leaflet)
- Documentos públicos
- Sistema de notificaciones toast
- Guards de autenticación por ruta
- Seeder de datos iniciales

---

## Equipo

Proyecto universitario — Grupo 8, JyAA 2025, UNLP.
