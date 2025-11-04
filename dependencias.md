Qué incluye (resumen rápido)

Estructura controller → service → repository → entity → util

Endpoints REST para CRUD y estadísticas

Servicio para cargar CSV vía POST /api/impuestos/upload-csv (multipart file)

Exportar a Excel descargable GET /api/impuestos/{sticker}/export

application.yml, pom.xml, .gitignore, README.md, y un data.csv de ejemplo

Tests básicos (context load)

Instrucciones rápidas

Descarga y extrae el ZIP.

Abre IntelliJ → Open → selecciona la carpeta del proyecto.

Espera que Maven descargue dependencias.

Ajusta src/main/resources/application.yml si tus credenciales MySQL cambian.

Ejecuta ImpuestosApplication (Run).

Prueba con Postman:

POST /api/impuestos (JSON)

POST /api/impuestos/upload-csv (form-data, key file)

GET /api/impuestos/fecha/2025-01-01

GET /api/impuestos/{sticker}/export


<project xmlns="http://maven.apache.org/POM/4.0.0"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>impuestos-app</artifactId>
    <version>1.0.0</version>
    <name>impuestos-app</name>
    <description>Gestión de impuestos con carga CSV y exportación Excel</description>

    <properties>
        <java.version>17</java.version>
        <spring.boot.version>3.3.3</spring.boot.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>5.9</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.5</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.28</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
