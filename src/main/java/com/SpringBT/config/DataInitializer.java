package com.SpringBT.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.SpringBT.entity.Cliente;
import com.SpringBT.entity.Review;
import com.SpringBT.repository.ClienteRepository;
import com.SpringBT.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {
        try {
            if (clienteRepository.count() > 0) {
                System.out.println("✓ Datos ya cargados. Saltando inicialización.");
                return;
            }

            List<Cliente> clientes = new ArrayList<>();

            // Clientes de diferentes géneros y edades para los gráficos
            Cliente c1 = new Cliente();
            c1.setNombre("Juan García");
            c1.setEdad(28);
            c1.setGenero("Masculino");
            c1.setIntolerancia(false);
            clientes.add(c1);

            Cliente c2 = new Cliente();
            c2.setNombre("María López");
            c2.setEdad(35);
            c2.setGenero("Femenino");
            c2.setIntolerancia(true);
            c2.setDetalleIntolerancia("Intolerancia a la lactosa");
            clientes.add(c2);

            Cliente c3 = new Cliente();
            c3.setNombre("Carlos Martínez");
            c3.setEdad(12);
            c3.setGenero("Masculino");
            c3.setIntolerancia(false);
            clientes.add(c3);

            Cliente c4 = new Cliente();
            c4.setNombre("Ana Rodríguez");
            c4.setEdad(22);
            c4.setGenero("Femenino");
            c4.setIntolerancia(true);
            c4.setDetalleIntolerancia("Celiaca - Intolerancia al gluten");
            clientes.add(c4);

            Cliente c5 = new Cliente();
            c5.setNombre("Pedro Sánchez");
            c5.setEdad(55);
            c5.setGenero("Masculino");
            c5.setIntolerancia(false);
            clientes.add(c5);

            Cliente c6 = new Cliente();
            c6.setNombre("Laura Fernández");
            c6.setEdad(19);
            c6.setGenero("Femenino");
            c6.setIntolerancia(false);
            clientes.add(c6);

            Cliente c7 = new Cliente();
            c7.setNombre("Miguel Torres");
            c7.setEdad(31);
            c7.setGenero("Masculino");
            c7.setIntolerancia(true);
            c7.setDetalleIntolerancia("Alergia a frutos secos");
            clientes.add(c7);

            Cliente c8 = new Cliente();
            c8.setNombre("Sofía Ruiz");
            c8.setEdad(67);
            c8.setGenero("Femenino");
            c8.setIntolerancia(false);
            clientes.add(c8);

            Cliente c9 = new Cliente();
            c9.setNombre("David Moreno");
            c9.setEdad(45);
            c9.setGenero("Masculino");
            c9.setIntolerancia(false);
            clientes.add(c9);

            Cliente c10 = new Cliente();
            c10.setNombre("Elena Jiménez");
            c10.setEdad(29);
            c10.setGenero("Femenino");
            c10.setIntolerancia(true);
            c10.setDetalleIntolerancia("Intolerancia al gluten");
            clientes.add(c10);

            // Guardar todos los clientes
            clienteRepository.saveAll(clientes);

            // Crear reviews con diferentes valoraciones (1-5 estrellas)
            List<Review> reviews = new ArrayList<>();

            Review r1 = new Review();
            r1.setDescripcion("Excelente servicio, muy recomendable. El personal es muy amable y profesional.");
            r1.setValoracion(5);
            r1.setCliente(c1);
            reviews.add(r1);

            Review r2 = new Review();
            r2.setDescripcion("Buen servicio en general, aunque el tiempo de espera fue un poco largo.");
            r2.setValoracion(4);
            r2.setCliente(c2);
            reviews.add(r2);

            Review r3 = new Review();
            r3.setDescripcion("Servicio correcto, nada destacable. Cumplió con lo esperado.");
            r3.setValoracion(3);
            r3.setCliente(c3);
            reviews.add(r3);

            Review r4 = new Review();
            r4.setDescripcion("Me encantó la experiencia, volveré sin duda. Todo perfecto.");
            r4.setValoracion(5);
            r4.setCliente(c4);
            reviews.add(r4);

            Review r5 = new Review();
            r5.setDescripcion("Regular, el servicio podría mejorar bastante. No quedé satisfecho.");
            r5.setValoracion(2);
            r5.setCliente(c5);
            reviews.add(r5);

            Review r6 = new Review();
            r6.setDescripcion("Muy buena atención y calidad. Recomendado.");
            r6.setValoracion(4);
            r6.setCliente(c6);
            reviews.add(r6);

            Review r7 = new Review();
            r7.setDescripcion("Malo. No volvería. Muy decepcionado con el servicio.");
            r7.setValoracion(1);
            r7.setCliente(c7);
            reviews.add(r7);

            // Guardar todas las reviews
            reviewRepository.saveAll(reviews);

            System.out.println("═══════════════════════════════════════════");
            System.out.println("✓ Datos de ejemplo cargados correctamente");
            System.out.println("  - Clientes creados: " + clienteRepository.count());
            System.out.println("  - Reviews creadas: " + reviewRepository.count());
            System.out.println("═══════════════════════════════════════════");

        } catch (Exception e) {
            System.err.println("✗ Error al cargar datos de ejemplo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
