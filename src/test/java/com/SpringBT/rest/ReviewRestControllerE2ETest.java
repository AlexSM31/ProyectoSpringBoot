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

import com.SpringBT.dto.ReviewDTO;
import com.SpringBT.entity.Cliente;
import com.SpringBT.repository.ClienteRepository;

import tools.jackson.databind.ObjectMapper;

/**
 * Test E2E con MockMvc (compatible con Spring Boot 4.x).
 */
@SpringBootTest
class ReviewRestControllerE2ETest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    private MockMvc mockMvc;
    private Long clienteId;

    /**
     * Configuramos MockMvc y creamos un cliente de prueba antes de cada test.
     */
    @BeforeEach
    void setUp() {
        // Configurar MockMvc
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        // Crear cliente para las reviews
        Cliente cliente = new Cliente();
        cliente.setNombre("Laura Sánchez");
        cliente.setEdad(32);
        cliente.setGenero("Femenino");
        cliente.setIntolerancia(false);
        Cliente guardado = clienteRepository.save(cliente);
        clienteId = guardado.getId();
    }

    /**
     * TEST 1: Verificar que GET /api/reviews devuelve código 200 OK.
     */
    @Test
    void testGetAllReviews() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * TEST 2: Verificar que POST /api/reviews crea una review
     * 
     * Verifica:
     * - Código 201 CREATED
     * - JSON con valoración correcta
     */
    @Test
    void testCreateReview() throws Exception {
        // 1. Creamos el DTO
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setDescripcion("Servicio impecable y comida deliciosa");
        reviewDTO.setValoracion(5);
        reviewDTO.setClienteId(clienteId);

        // 2. Convertimos a JSON
        String reviewJson = objectMapper.writeValueAsString(reviewDTO);

        // 3. Simulamos petición POST
        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reviewJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.valoracion").value(5))
                .andExpect(jsonPath("$.descripcion").value("Servicio impecable y comida deliciosa"));
    }
}
