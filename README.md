# ProyectoNo2IPC2

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 22.1.8.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.

## Sesión y reportes PDF

Las rutas de los paneles están protegidas con un guard de Angular según el rol. El interceptor agrega el token Bearer a las solicitudes protegidas y cierra la sesión local cuando la API responde `401`. Los tokens vencen después de ocho horas.

El backend firma los tokens con `SESSION_TOKEN_SECRET`. Configura esa variable con un valor secreto aleatorio de al menos 32 bytes en el entorno de Tomcat para conservar la validez de las sesiones al reiniciar el servidor. Si no está configurada, se genera una clave temporal al iniciar la aplicación, por lo que los tokens existentes dejan de ser válidos cuando se reinicia.

Los reportes JasperReports se descargan desde el menú **Reportes**:

- Historial de pagos por estudiante: disponible para ADMIN y SUPERADMIN.
- Ingresos por concepto: disponible para SUPERADMIN.
- Morosidad de biblioteca: disponible para BIBLIOTECARIO, ADMIN y SUPERADMIN.

Las fechas de los dos primeros reportes son opcionales; deben enviarse como `yyyy-MM-dd` y la fecha inicial no puede ser posterior a la final.
