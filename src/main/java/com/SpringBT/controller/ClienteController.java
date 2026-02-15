package com.SpringBT.controller;

import com.SpringBT.entity.Cliente;
import com.SpringBT.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

	private final ClienteService clienteService;

	// 1. Listar todos los clientes
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("titulo", "Lista de Clientes");
		return "clientes/lista";
	}

	// 2. Mostrar formulario para crear nuevo cliente
	@GetMapping("/nuevo")
	public String mostrarFormularioCrear(Model model) {
		Cliente cliente = new Cliente();
		cliente.setIntolerancia(false); // Valor por defecto
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Nuevo Cliente");
		model.addAttribute("accion", "nuevo");
		return "clientes/formulario";
	}

	// 2. Guardar cliente (crear o actualizar)
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Cliente cliente, RedirectAttributes redirectAttributes) {
		try {
			clienteService.save(cliente);
			redirectAttributes.addFlashAttribute("mensaje", "Cliente guardado exitosamente");
			redirectAttributes.addFlashAttribute("tipo", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "Error al guardar el cliente");
			redirectAttributes.addFlashAttribute("tipo", "danger");
		}
		return "redirect:/clientes";
	}

	// 3. Mostrar formulario para editar cliente
	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Cliente cliente = clienteService.findById(id).orElse(null);

		if (cliente == null) {
			redirectAttributes.addFlashAttribute("mensaje", "Cliente no encontrado");
			redirectAttributes.addFlashAttribute("tipo", "warning");
			return "redirect:/clientes";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Editar Cliente");
		model.addAttribute("accion", "editar");
		return "clientes/formulario";
	}

	// 4. Eliminar cliente
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			clienteService.deleteById(id);
			redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado exitosamente");
			redirectAttributes.addFlashAttribute("tipo", "success");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar el cliente");
			redirectAttributes.addFlashAttribute("tipo", "danger");
		}
		return "redirect:/clientes";
	}
}
