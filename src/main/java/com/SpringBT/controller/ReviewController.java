package com.SpringBT.controller;

import com.SpringBT.entity.Cliente;
import com.SpringBT.entity.Review;
import com.SpringBT.service.ClienteService;
import com.SpringBT.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final ClienteService clienteService;

	// 1. Listar todas las reviews
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("reviews", reviewService.findAll());
		model.addAttribute("titulo", "Lista de Reviews");
		return "reviews/lista";
	}

	// 2. Mostrar formulario para crear nueva review
	@GetMapping("/nuevo")
	public String mostrarFormularioCrear(Model model) {
		model.addAttribute("review", new Review());
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("titulo", "Nueva Review");
		model.addAttribute("accion", "nuevo");
		return "reviews/formulario";
	}

	// 2. Guardar review (crear o actualizar)
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Review review, @RequestParam Long clienteId,
			RedirectAttributes redirectAttributes) {
		try {
			Cliente cliente = clienteService.findById(clienteId).orElse(null);
			if (cliente == null) {
				redirectAttributes.addFlashAttribute("mensaje", "Cliente no encontrado");
				redirectAttributes.addFlashAttribute("tipo", "danger");
				return "redirect:/reviews";
			}

			review.setCliente(cliente);
			reviewService.save(review);
			redirectAttributes.addFlashAttribute("mensaje", "Review guardada exitosamente");
			redirectAttributes.addFlashAttribute("tipo", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "Error al guardar la review: " + e.getMessage());
			redirectAttributes.addFlashAttribute("tipo", "danger");
		}
		return "redirect:/reviews";
	}

	// 3. Mostrar formulario para editar review
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Review review = reviewService.findById(id).orElse(null);

		if (review == null) {
			redirectAttributes.addFlashAttribute("mensaje", "Review no encontrada");
			redirectAttributes.addFlashAttribute("tipo", "warning");
			return "redirect:/reviews";
		}

		model.addAttribute("review", review);
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("titulo", "Editar Review");
		model.addAttribute("accion", "editar");
		return "reviews/formulario";
	}

	// 4. Eliminar review
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			reviewService.deleteById(id);
			redirectAttributes.addFlashAttribute("mensaje", "Review eliminada exitosamente");
			redirectAttributes.addFlashAttribute("tipo", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar la review");
			redirectAttributes.addFlashAttribute("tipo", "danger");
		}
		return "redirect:/reviews";
	}
}
