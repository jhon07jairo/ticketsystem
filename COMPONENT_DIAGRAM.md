# Diagrama de Componentes - Sistema de Tickets

```
+----------------------------------+
|                                  |
|  Cliente (Navegador/API Client)  |
|                                  |
+----------------+----------------+
                 |
                 | HTTP Requests/Responses
                 |
+----------------v----------------+
|                                  |
|        API REST (Controller)     |
|                                  |
|  +----------------------------+  |
|  |                            |  |
|  |    TicketController        |  |
|  |                            |  |
|  +----------------------------+  |
|                                  |
+----------------+----------------+
                 |
                 | Llamadas a métodos
                 |
+----------------v----------------+
|                                  |
|      Capa de Servicio            |
|                                  |
|  +----------------------------+  |
|  |                            |  |
|  |    TicketService           |  |
|  |                            |  |
|  +----------------------------+  |
|                                  |
+----------------+----------------+
                 |
                 | Llamadas a métodos
                 |
+----------------v----------------+
|                                  |
|      Capa de Repositorio         |
|                                  |
|  +----------------------------+  |
|  |                            |  |
|  |    TicketRepository        |  |
|  |                            |  |
|  +----------------------------+  |
|                                  |
+----------------+----------------+
                 |
                 | Consultas SQL (JDBC)
                 |
+----------------v----------------+
|                                  |
|         Base de Datos            |
|                                  |
|  +----------------------------+  |
|  |                            |  |
|  |    SQL Server              |  |
|  |    - Tickets               |  |
|  |    - Estados               |  |
|  |    - TicketsNoResueltosLog |  |
|  |                            |  |
|  +----------------------------+  |
|                                  |
+----------------------------------+
```

## Descripción de Componentes

### Cliente
- Navegador web o cualquier cliente API que consuma los endpoints REST.

### API REST (Controller)
- **TicketController**: Maneja las solicitudes HTTP y devuelve respuestas apropiadas.
  - Endpoints para crear, listar, actualizar estado y marcar como resuelto tickets.

### Capa de Servicio
- **TicketService**: Implementa la lógica de negocio.
  - Validación de datos.
  - Orquestación de operaciones.
  - Manejo de excepciones.

### Capa de Repositorio
- **TicketRepository**: Gestiona el acceso a datos.
  - Implementa operaciones CRUD para tickets.
  - Utiliza JDBC Template para ejecutar consultas SQL.

### Base de Datos
- **SQL Server**: Almacena los datos de la aplicación.
  - Tabla Tickets: Almacena información de tickets.
  - Tabla Estados: Almacena los posibles estados de un ticket.
  - Vista TicketsNoResueltosLog: Muestra tickets no resueltos en 30 días.

## Flujo de Datos

1. El cliente envía una solicitud HTTP a uno de los endpoints de la API.
2. El controlador recibe la solicitud, extrae los parámetros y llama al servicio correspondiente.
3. El servicio aplica la lógica de negocio y llama al repositorio para acceder a los datos.
4. El repositorio ejecuta consultas SQL en la base de datos y devuelve los resultados.
5. El servicio procesa los resultados y los devuelve al controlador.
6. El controlador formatea la respuesta y la envía al cliente.

## Arquitectura de Despliegue con Docker

```
+----------------------------------+
|                                  |
|      Docker Host                 |
|                                  |
|  +----------------------------+  |
|  |                            |  |
|  |  Container: app            |  |
|  |  - Spring Boot Application |  |
|  |                            |  |
|  +------------+---------------+  |
|               |                  |
|               | Network          |
|               |                  |
|  +------------v---------------+  |
|  |                            |  |
|  |  Container: db             |  |
|  |  - SQL Server              |  |
|  |                            |  |
|  +----------------------------+  |
|                                  |
+----------------------------------+
```

En esta arquitectura de despliegue:
1. La aplicación Spring Boot se ejecuta en un contenedor Docker.
2. La base de datos SQL Server se ejecuta en otro contenedor Docker.
3. Ambos contenedores se comunican a través de una red Docker.
4. Los datos de la base de datos se persisten en un volumen Docker.