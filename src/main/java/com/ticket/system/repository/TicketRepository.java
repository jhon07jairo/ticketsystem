package com.ticket.system.repository;

import com.ticket.system.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public class TicketRepository implements ITicketRepository{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int crear(Ticket ticket) {
        String sql = "INSERT INTO Tickets (titulo, descripcion, fechaCreacion) VALUES (?, ?, GETDATE())";
        return jdbcTemplate.update(sql, new Object[]{
            ticket.getTitulo(),
            ticket.getDescripcion()
        });
    }

    @Override
    public List<Ticket> obtenerTodos() {
        String sql = "SELECT * FROM Tickets";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Ticket.class));
    }

    @Override
    public List<Ticket> obtenerPorId(Long id) {
        String sql = "SELECT * FROM Tickets WHERE id = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Ticket.class), id);
    }

    @Override
    public int actualizarEstado(Ticket ticket) {
        String sql = "UPDATE Tickets SET estadoId = ?, fechaActualizacion = GETDATE() WHERE id = ?";
        return jdbcTemplate.update(sql,
                ticket.getEstadoId(),
                ticket.getId()
        );
    }

    @Override
    public int marcarComoResuelto(Ticket ticket) {
        String sql = "UPDATE Tickets SET estadoId = 4, comentario = ?, fechaActualizacion = GETDATE() WHERE id = ?";
        return jdbcTemplate.update(sql,
                ticket.getComentario(),
                ticket.getId()
        );
    }

    @Override
    public List<Ticket> obtenerNoResueltos30Dias() {
        String sql = "SELECT * FROM TicketsNoResueltosLog";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Ticket.class));
    }
}
