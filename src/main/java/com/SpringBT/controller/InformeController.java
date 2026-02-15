package com.SpringBT.controller;

import com.SpringBT.service.ClienteService;
import com.SpringBT.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/informes")
@RequiredArgsConstructor
public class InformeController {

	private final ClienteService clienteService;
	private final ReviewService reviewService;

	@GetMapping
	public String mostrarInformes(Model model) {
		model.addAttribute("titulo", "Dashboard - Informes KPI");
		return "informes/dashboard";
	}

	// API REST para obtener datos de clientes por género
	@GetMapping("/api/clientes-genero")
	@ResponseBody
	public Map<String, Object> getClientesPorGenero() {
		List<Object[]> datos = clienteService.getClientesPorGenero();
		Map<String, Object> resultado = new HashMap<>();

		String[] labels = datos.stream().map(d -> (String) d[0]).toArray(String[]::new);
		Long[] values = datos.stream().map(d -> (Long) d[1]).toArray(Long[]::new);

		resultado.put("labels", labels);
		resultado.put("data", values);
		return resultado;
	}

	// API REST para obtener datos de reviews por valoración
	@GetMapping("/api/reviews-valoracion")
	@ResponseBody
	public Map<String, Object> getReviewsPorValoracion() {
		List<Object[]> datos = reviewService.getReviewsPorValoracion();
		Map<String, Object> resultado = new HashMap<>();

		// Asegurar que todas las valoraciones (1-5) estén presentes
		Map<Integer, Long> valoraciones = new HashMap<>();
		for (int i = 1; i <= 5; i++) {
			valoraciones.put(i, 0L);
		}

		for (Object[] dato : datos) {
			valoraciones.put((Integer) dato[0], (Long) dato[1]);
		}

		Integer[] labels = { 1, 2, 3, 4, 5 };
		Long[] values = { valoraciones.get(1), valoraciones.get(2), valoraciones.get(3), valoraciones.get(4),
				valoraciones.get(5) };

		resultado.put("labels", labels);
		resultado.put("data", values);
		return resultado;
	}

	// API REST para obtener datos de clientes por rango de edad
	@GetMapping("/api/clientes-edad")
	@ResponseBody
	public Map<String, Object> getClientesPorEdad() {
		List<Object[]> datos = clienteService.getClientesPorRangoEdad();
		Map<String, Object> resultado = new HashMap<>();

		String[] labels = datos.stream().map(d -> (String) d[0]).toArray(String[]::new);
		Long[] values = datos.stream().map(d -> (Long) d[1]).toArray(Long[]::new);

		resultado.put("labels", labels);
		resultado.put("data", values);
		return resultado;
	}

	// API REST para gráfico personalizado: Clientes con/sin intolerancias
	@GetMapping("/api/clientes-intolerancia")
	@ResponseBody
	public Map<String, Object> getClientesPorIntolerancia() {
		List<Object[]> datos = clienteService.getClientesPorIntolerancia();
		Map<String, Object> resultado = new HashMap<>();

		String[] labels = datos.stream().map(d -> (String) d[0]).toArray(String[]::new);
		Long[] values = datos.stream().map(d -> (Long) d[1]).toArray(Long[]::new);

		resultado.put("labels", labels);
		resultado.put("data", values);
		return resultado;
	}
}
