package com.ticket.system.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Estados {
    @Id
    private Long id;
    private String nombre;
}
