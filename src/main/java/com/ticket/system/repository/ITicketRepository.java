package com.ticket.system.repository;

import com.ticket.system.model.Ticket;

import java.util.List;

public interface ITicketRepository{
    //void crear(Ticket ticket);
    public int crear(Ticket ticket);
    List<Ticket> obtenerTodos();
    List<Ticket> obtenerPorId(Long id);
    //void actualizarEstado(Long id, String nuevoEstado);
    public int actualizarEstado(Long id, Long estadoId);
    public int marcarComoResuelto(Ticket ticket);
    List<Ticket> obtenerNoResueltos30Dias();
}
