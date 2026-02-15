package com.SpringBT.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.SpringBT.dto.ClienteDTO;

import tools.jackson.databind.ObjectMapper;

/**
 * Test E2E con MockMvc (compatible con Spring Boot 4.x).
 * 
 * ¿Qué probamos?
 * - Endpoints HTTP completos (GET, POST)
 * - Códigos de estado (200 OK, 201 CREATED)
 * - Respuestas JSON
 * 
 * Técnica: MockMvc simula peticiones HTTP sin levantar servidor real.
 */
@SpringBootTest
class ClienteRestControllerE2ETest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    /**
     * Configuramos MockMvc antes de cada test.
     * Esto reemplaza la necesidad de @AutoConfigureMockMvc.
     */
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    /**
     * TEST 1: Verificar que GET /api/clientes devuelve código 200 OK.
     * 
     * Simula: Un cliente HTTP haciendo GET a la API
     * Verifica: Código de estado HTTP 200
     */
    @Test
    void testGetAllClientes() throws Exception {
        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * TEST 2: Verificar que POST /api/clientes crea un cliente.
     * 
     * Simula: Un cliente HTTP enviando JSON con datos de cliente
     * Verifica: 
     * - Código 201 CREATED
     * - JSON de respuesta con el nombre correcto
     */
    @Test
    void testCreateCliente() throws Exception {
        // 1. Creamos el DTO con datos de prueba
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Carlos Ruiz");
        clienteDTO.setEdad(40);
        clienteDTO.setGenero("Masculino");
        clienteDTO.setIntolerancia(false);

        // 2. Convertimos el DTO a JSON
        String clienteJson = objectMapper.writeValueAsString(clienteDTO);

        // 3. Simulamos la petición POST con MockMvc
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Carlos Ruiz"))
                .andExpect(jsonPath("$.edad").value(40));
    }
}
