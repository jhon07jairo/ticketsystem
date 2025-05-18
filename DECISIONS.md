# Decisiones de Diseño - Sistema de Tickets

Este documento describe las decisiones de diseño tomadas durante el desarrollo del Sistema de Tickets.

## Arquitectura

### Patrón de Arquitectura

Se ha implementado una arquitectura en capas siguiendo el patrón MVC (Modelo-Vista-Controlador):

1. **Capa de Controlador**: Maneja las solicitudes HTTP y devuelve respuestas apropiadas.
2. **Capa de Servicio**: Contiene la lógica de negocio de la aplicación.
3. **Capa de Repositorio**: Gestiona el acceso a datos y la persistencia.
4. **Capa de Modelo**: Define las entidades y objetos de transferencia de datos.

Esta arquitectura proporciona una clara separación de responsabilidades, lo que facilita el mantenimiento y la escalabilidad de la aplicación.

## Tecnologías

### Spring Boot

Se eligió Spring Boot como framework principal por:

1. **Facilidad de configuración**: Minimiza la configuración boilerplate.
2. **Amplio ecosistema**: Proporciona soluciones integradas para web, datos, seguridad, etc.
3. **Soporte para microservicios**: Facilita la evolución hacia una arquitectura de microservicios si es necesario.

### SQL Server

Se eligió SQL Server como base de datos por:

1. **Robustez**: Ofrece un sistema de gestión de bases de datos confiable y maduro.
2. **Compatibilidad con Spring**: Buena integración con Spring Data JDBC.
3. **Soporte para transacciones**: Garantiza la integridad de los datos en operaciones complejas.

## Decisiones de Implementación

### Uso de JDBC Template en lugar de JPA/Hibernate

Se optó por utilizar Spring JDBC Template en lugar de JPA/Hibernate por:

1. **Control más directo**: Permite un control más preciso sobre las consultas SQL.
2. **Menor sobrecarga**: Reduce la complejidad y sobrecarga que puede introducir un ORM completo.
3. **Rendimiento**: Potencialmente mejor rendimiento para operaciones simples de CRUD.

### Dockerización

La aplicación se ha dockerizado para:

1. **Portabilidad**: Garantiza que la aplicación funcione de manera consistente en diferentes entornos.
2. **Aislamiento**: Evita conflictos con otras aplicaciones o dependencias del sistema.
3. **Facilidad de despliegue**: Simplifica el proceso de despliegue y escalado.

Se utilizó un enfoque multi-contenedor con Docker Compose para:

1. **Separación de responsabilidades**: La aplicación y la base de datos se ejecutan en contenedores separados.
2. **Facilidad de configuración**: Docker Compose simplifica la configuración y orquestación de múltiples contenedores.
3. **Persistencia de datos**: Se configuró un volumen para garantizar la persistencia de los datos de la base de datos.

### Pruebas Unitarias

Se implementaron pruebas unitarias para:

1. **Garantizar la calidad**: Verificar que cada componente funcione correctamente de forma aislada.
2. **Facilitar el mantenimiento**: Las pruebas sirven como documentación viva y ayudan a detectar regresiones.
3. **Mejorar el diseño**: El enfoque en la prueba lleva a un mejor diseño de código, más modular y menos acoplado.

## Decisiones de Diseño de API

### Endpoints RESTful

Se diseñó una API RESTful para:

1. **Simplicidad**: Interfaces claras y predecibles.
2. **Escalabilidad**: Facilita la evolución y expansión de la API.
3. **Compatibilidad**: Ampliamente soportada por herramientas y frameworks modernos.

### Respuestas Estandarizadas

Se implementó un formato de respuesta estandarizado (ServiceResponse) para:

1. **Consistencia**: Proporciona una estructura uniforme para todas las respuestas.
2. **Claridad**: Facilita la interpretación de los resultados por parte de los clientes.
3. **Manejo de errores**: Permite comunicar errores de manera estructurada y comprensible.

## Conclusión

Las decisiones de diseño tomadas en este proyecto se han orientado a crear una aplicación robusta, mantenible y escalable, utilizando tecnologías modernas y siguiendo buenas prácticas de desarrollo de software.