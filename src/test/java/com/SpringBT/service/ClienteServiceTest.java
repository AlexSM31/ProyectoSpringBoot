package com.SpringBT.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBT.entity.Cliente;
import com.SpringBT.repository.ClienteRepository;

/**
 * Test Unitario SIMPLIFICADO de ClienteService.
 * 
 * ¿Qué hacemos?
 * - Probamos 2 métodos del servicio
 * - Usamos mocks (simulamos el repositorio)
 * - NO usamos base de datos real
 */
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Creamos un cliente de prueba
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Pérez");
        cliente.setEdad(25);
        cliente.setGenero("Masculino");
        cliente.setIntolerancia(false);
    }

    /**
     * TEST 1: Verificar que findAll() devuelve la lista de clientes.
     */
    @Test
    void testFindAll() {
        // Simulamos que el repositorio devuelve una lista con 1 cliente
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        // Llamamos al método del servicio
        List<Cliente> resultado = clienteService.findAll();

        // Verificamos que funciona correctamente
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    /**
     * TEST 2: Verificar que save() guarda el cliente.
     */
    @Test
    void testSave() {
        // Simulamos que el repositorio guarda el cliente
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Llamamos al método del servicio
        Cliente resultado = clienteService.save(cliente);

        // Verificamos que el cliente se guardó
        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals(25, resultado.getEdad());
    }
}
