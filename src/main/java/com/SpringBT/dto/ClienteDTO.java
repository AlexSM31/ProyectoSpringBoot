package com.SpringBT.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para transferir datos de Cliente en la API REST.
 * Usamos DTOs para:
 * Evitar exponer entidades directamente
 * Prevenir serialización circular con Review
 * Validar datos de entrada
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    // notblank valida que el campo no este vacio o solo con espacios
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    // not null que el campo no esa null
    // min valida valor minimo numerico
    @NotNull(message = "La edad no puede ser nula")
    @Min(value = 0, message = "La edad debe ser un valor positivo")
    private Integer edad;

    @NotBlank(message = "El género no puede estar vacío")
    private String genero;

    @NotNull(message = "El campo intolerancia no puede ser nulo")
    private Boolean intolerancia;

    // valida la longitud del string
    @Size(max = 500, message = "El detalle de intolerancia no puede exceder 500 caracteres")
    private String detalleIntolerancia;

    // ID de la review asociada (si existe)
    private Long reviewId;
}
