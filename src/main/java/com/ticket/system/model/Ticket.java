package com.ticket.system.model;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Ticket {
    @Id
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaVencimiento;
    private String comentario;

    private Long estadoId;
}
