# Projektname: CoolCars Deployment mit Docker und Azure

#### Das Backend:
https://coolcarsbe.azurewebsites.net/cars

#### Das Frontend:
https://coolcarsfe.azurewebsites.net/

## 1. Projektübersicht

**Beschreibung**: Bei diesem Projekt wurde uns ein Backend und ein Frontend zur verfügung gestellt. Wir haben dann dazu zwei Dockerfiles und das docker-compose.yml file geschrieben.
Zum Schluss haben wir dann die App Über Azure depolyed.

### **Technologien**:

- Frontend: React (vorgegeben)
- Backend: Node.js (Express) (vorgegeben)
- Docker
- Docker Compose
- Azure (zum deployen der App)

## 2. Verzeichnisstruktur
``` sh
MyWebApp/
├── frontend/
│   ├── Dockerfile
│   ├── src/
│   └── ...
├── backend/
│   ├── Dockerfile
│   ├── app/
│   └── ...
├── docker-compose.yml
└── README.md
```

## 3. Dockerfile für das Frontend

**Pfad**: `m347-ref-card-03-fe/Dockerfile`
**Inhalt und Erklärungen**:
``` dockerfile
# Basis-Image: Node.js Version 18.17.0 mit bullseye-slim als Basis
FROM node:18.17.0-bullseye-slim
# Setzt das Arbeitsverzeichnis innerhalb des Containers auf /app
WORKDIR /app
# Kopiert den gesamten Inhalt des aktuellen Verzeichnisses auf dem Host in das Arbeitsverzeichnis des Containers
COPY . .
# Installiert die benötigten Abhängigkeiten anhand der package-lock.json Datei
RUN npm ci
# Baut die Anwendung (z.B. transpiliert TypeScript, erstellt Produktions-Bundles)
RUN npm run build
# Öffnet Port 3000, damit der Container darauf zugreifen kann
EXPOSE 3000
# Definiert den Befehl, der ausgeführt wird, wenn der Container gestartet wird
ENTRYPOINT ["npm", "start"]

```

## 4. Dockerfile für das Backend
**Pfad**: `m347-ref-card-03-be/Dockerfile`
**Inhalt und Erklärung:**
``` dockerfile
# Erster Build-Stage: Maven 3.8.4 mit OpenJDK 17 als Basis-Image
FROM maven:3.8.4-openjdk-17 AS builder
# Setzt das Arbeitsverzeichnis innerhalb des Containers auf /build
WORKDIR /build
# Kopiert den gesamten Inhalt des aktuellen Verzeichnisses auf dem Host in das Arbeitsverzeichnis des Containers
COPY . /build
# Führt Maven-Befehle aus, um das Projekt zu säubern und das Paket zu erstellen
RUN mvn -B clean package

# Zweiter Build-Stage: OpenJDK 17 JDK in der schlanken Version als Basis-Image
FROM openjdk:17-jdk-slim
# Setzt das Arbeitsverzeichnis innerhalb des Containers auf /app
WORKDIR /app
# Kopiert das erstellte JAR-File vom ersten Stage ins aktuelle Arbeitsverzeichnis
COPY --from=builder /build/target/*.jar app.jar
# Öffnet Port 8080, damit der Container darauf zugreifen kann
EXPOSE 8080
# Definiert den Befehl, der ausgeführt wird, wenn der Container gestartet wird
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 5. docker-compose.yml
**Pfad**: `Docker-Compose.yml`
**Inhalt**:
``` yml
version: '3.8'

services:
  frontend:
    build:
      context: ./m347-ref-card-03-fe
    networks:
      - cool-cars-network
    ports:
      - "3000:3000"
    restart: always

  backend:
    build:
      context: ./m347-ref-card-03-be
    networks:
      - cool-cars-network
    ports:
      - "8080:8080"
    restart: always

networks:
  cool-cars-network:
    driver: bridge
```
- Definiert zwei Services: `frontend` und `backend`.
- Beide Services werden aus ihren jeweiligen Dockerfiles gebaut.
- Ports werden entsprechend gemappt.
- Umgebungseinstellungen für das Backend.


# 7. Deployen auf Azure

![[fetch.jpg]]
Das sind die Zeilen, die im Backend abgeändert wurden. Die Annotation "@CrossOrigin" ermöglicht Cross-Origin Requests von allen Ursprüngen, sodass Anfragen von verschiedenen Domains an diesen Controller gesendet werden können. Das Sternchen (`*`) steht dabei für alle Ursprünge. Dies ist besonders nützlich, wenn Frontend und Backend auf verschiedenen Domains laufen.


![[fetchreal.jpg]]
Hier ist die Funktion aus dem Frontend, die die Daten aus dem Backen fetchet


![[zweiWebApps.jpg]]
Das sind die Beiden Webapps auf denen das Backend und das Frontend laufen. 


![[repositorys.jpg]]
![repositorys.jpg](https://github.com/klionCH/CoolCars/blob/main/repositorys.jpg?raw=true)




