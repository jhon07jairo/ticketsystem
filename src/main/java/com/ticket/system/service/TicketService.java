package com.ticket.system.service;

import com.ticket.system.model.Ticket;
import com.ticket.system.repository.ITicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService{

    @Autowired
    private ITicketRepository iTicketRepository;

    @Override
    public int crear(Ticket ticket) {
        int row;
        try {
            row = iTicketRepository.crear(ticket);
        } catch (Exception ex) {
            throw ex;
        }
        return row;
    }

    @Override
    public List<Ticket> obtenerTodos() {
        List<Ticket> list;
        try {
            list = iTicketRepository.obtenerTodos();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public List<Ticket> obtenerPorId(Long id) {
        List<Ticket> list;
        try {
            list = iTicketRepository.obtenerPorId(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    @Override
    public int actualizarEstado(Long id, Long estadoId) {
        int row;
        try {
            row = iTicketRepository.actualizarEstado(id, estadoId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return row;
    }

    @Override
    public int marcarComoResuelto(Ticket ticket) {
        int row;
        try {
            row = iTicketRepository.marcarComoResuelto(ticket);
        } catch (Exception ex) {
            throw ex;
        }
        return row;
    }

    @Override
    public List<Ticket> obtenerNoResueltos30Dias() {
        List<Ticket> list;
        try {
            list = iTicketRepository.obtenerNoResueltos30Dias();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

}
