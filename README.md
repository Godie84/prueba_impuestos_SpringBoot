# impuestos-app
Proyecto Spring Boot para gestión de impuestos (importar CSV, consultar y exportar Excel).

## Cómo usar
- Importar en IntelliJ como **Maven project**.
- Configurar `src/main/resources/application.yml` con tus credenciales de MySQL.
- Ejecutar `ImpuestosApplication`.

Endpoints principales:
- POST  /api/impuestos       -> crear impuesto (JSON)
- GET   /api/impuestos/{sticker}
- GET   /api/impuestos/fecha/{yyyy-MM-dd}
- GET   /api/impuestos/horario/{tipo}

Utilidades:
- `CargarCSVService` para cargar CSV desde ruta.
- `ExportarExcel` para exportar un impuesto a .xlsx
# prueba_impuestos_SpringBoot
