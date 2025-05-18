# Cambios para Compatibilidad con SQL Server

Este documento describe los cambios realizados para asegurar que todas las consultas SQL en el proyecto sean compatibles con SQL Server.

## Cambios Realizados

### 1. Método `crear` en TicketRepository.java

**Antes:**
```java
@Override
public int crear(Ticket ticket) {
    String sql = "INSERT INTO Tickets (titulo, descripcion) VALUES (?, ?)";
    return jdbcTemplate.update(sql, new Object[]{
        ticket.getTitulo(),
        ticket.getDescripcion()
    });
}
```

**Después:**
```java
@Override
public int crear(Ticket ticket) {
    String sql = "INSERT INTO Tickets (titulo, descripcion, fechaCreacion) VALUES (?, ?, GETDATE())";
    return jdbcTemplate.update(sql, new Object[]{
        ticket.getTitulo(),
        ticket.getDescripcion()
    });
}
```

**Explicación:** Se modificó la consulta para utilizar la función `GETDATE()` de SQL Server para establecer la fecha de creación del ticket directamente en la base de datos, en lugar de depender del valor predeterminado definido en el esquema.

### 2. Método `actualizarEstado` en TicketRepository.java

**Antes:**
```java
@Override
public int actualizarEstado(Ticket ticket) {
    String sql = "UPDATE Tickets SET estadoId = ?, fechaActualizacion = ? WHERE id = ?";
    return jdbcTemplate.update(sql,
            ticket.getEstadoId(),
            LocalDateTime.now(),
            ticket.getId()
    );
}
```

**Después:**
```java
@Override
public int actualizarEstado(Ticket ticket) {
    String sql = "UPDATE Tickets SET estadoId = ?, fechaActualizacion = GETDATE() WHERE id = ?";
    return jdbcTemplate.update(sql,
            ticket.getEstadoId(),
            ticket.getId()
    );
}
```

**Explicación:** Se modificó la consulta para utilizar la función `GETDATE()` de SQL Server para establecer la fecha de actualización del ticket directamente en la base de datos, en lugar de pasar un valor desde Java.

### 3. Método `marcarComoResuelto` en TicketRepository.java

**Antes:**
```java
@Override
public int marcarComoResuelto(Ticket ticket) {
    String sql = "UPDATE Tickets SET estadoId = 4, comentario = ?, fechaActualizacion = ? WHERE id = ?";
    return jdbcTemplate.update(sql,
            ticket.getComentario(),
            LocalDateTime.now(),
            ticket.getId()
    );
}
```

**Después:**
```java
@Override
public int marcarComoResuelto(Ticket ticket) {
    String sql = "UPDATE Tickets SET estadoId = 4, comentario = ?, fechaActualizacion = GETDATE() WHERE id = ?";
    return jdbcTemplate.update(sql,
            ticket.getComentario(),
            ticket.getId()
    );
}
```

**Explicación:** Se modificó la consulta para utilizar la función `GETDATE()` de SQL Server para establecer la fecha de actualización del ticket directamente en la base de datos, en lugar de pasar un valor desde Java.

## Verificación de Compatibilidad

Se verificó que los siguientes archivos ya estaban utilizando sintaxis compatible con SQL Server:

1. **init-db.sql**: Utiliza comandos específicos de SQL Server como `GO`, tablas del sistema (`sys.databases`, `sys.tables`, `sys.views`), funciones como `GETDATE()`, `DATEADD()`, `DATEDIFF()`, y tipos de datos como `NVARCHAR(MAX)` y `DATETIME2`.

2. **test-data.sql**: Utiliza comandos específicos de SQL Server como `DBCC CHECKIDENT` para reiniciar columnas de identidad, y funciones como `GETDATE()`, `DATEADD()`, y `DATEDIFF()`.

3. **application.properties**: Configurado correctamente para SQL Server con la URL JDBC adecuada y el controlador de SQL Server.

4. **docker-compose.yml**: Configurado para utilizar la imagen oficial de Microsoft SQL Server y montar el script de inicialización.

## Conclusión

Todos los cambios realizados aseguran que las consultas SQL en el proyecto sean compatibles con SQL Server y aprovechen las funciones específicas de SQL Server cuando sea apropiado.