# ─── STAGE 1: Build Angular frontend ───────────────────────
FROM node:22-alpine AS frontend-build

WORKDIR /app/frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci
COPY frontend/ .
RUN npm run build
# Output generado en: /app/frontend/dist/JyAA/browser/

# ─── STAGE 2: Build Java backend (WAR) ──────────────────────
FROM maven:3.9-eclipse-temurin-17 AS backend-build

WORKDIR /app
COPY backend/pom.xml pom.xml
RUN mvn dependency:go-offline -B
COPY backend/src src

# Limpiar archivos Angular obsoletos del webapp y reemplazarlos
# con el build fresco del stage anterior
RUN find src/main/webapp -maxdepth 1 \( -name "*.js" -o -name "*.css" -o -name "index.html" \) -delete 2>/dev/null || true
COPY --from=frontend-build /app/frontend/dist/JyAA/browser/ src/main/webapp/

RUN mvn package -DskipTests -B
# WAR generado en: /app/target/centro-salud-1.0.0.war

# ─── STAGE 3: Runtime Tomcat ────────────────────────────────
FROM tomcat:11-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=backend-build /app/target/centro-salud-1.0.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
