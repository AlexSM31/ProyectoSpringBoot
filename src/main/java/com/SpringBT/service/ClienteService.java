package com.SpringBT.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBT.entity.Cliente;
import com.SpringBT.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }
    
    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @Transactional
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
    
    public long count() {
        return clienteRepository.count();
    }
    
    // Métodos para los informes
    public List<Object[]> getClientesPorGenero() {
        return clienteRepository.countByGenero();
    }
    
    public List<Object[]> getClientesPorRangoEdad() {
        return clienteRepository.countByRangoEdad();
    }
    
    public List<Object[]> getClientesPorIntolerancia() {
        return clienteRepository.countByIntolerancia();
    }
}
