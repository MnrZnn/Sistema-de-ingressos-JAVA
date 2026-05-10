package com.ingressos.controller;

import com.ingressos.model.*;
import com.ingressos.service.*;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;
    private final EventoService eventoService;
    private final UsuarioService usuarioService;

    public IngressoController(IngressoService ingressoService, EventoService eventoService, UsuarioService usuarioService) {
        this.ingressoService = ingressoService;
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String busca,
                         @RequestParam(required = false) String status,
                         @AuthenticationPrincipal UserDetails user,
                         Model model) {
        Usuario u = usuarioService.buscarPorUsername(user.getUsername()).orElseThrow();
        List<Ingresso> ingressos;

        if (busca != null && !busca.isBlank()) {
            ingressos = ingressoService.buscarPorEvento(busca).stream()
                    .filter(i -> i.getUsuarioId() != null && i.getUsuarioId().equals(u.getId())).toList();
        } else if (status != null && !status.isBlank()) {
            ingressos = ingressoService.buscarPorStatus(status).stream()
                    .filter(i -> i.getUsuarioId() != null && i.getUsuarioId().equals(u.getId())).toList();
        } else {
            ingressos = ingressoService.listarPorUsuario(u.getId());
        }

        long totalAtivos = ingressos.stream().filter(i -> "RESERVADO".equals(i.getStatus()) || "CONFIRMADO".equals(i.getStatus())).count();
        double totalReceita = ingressos.stream().filter(i -> !"CANCELADO".equals(i.getStatus())).mapToDouble(Ingresso::calcularValor).sum();

        model.addAttribute("ingressos", ingressos);
        model.addAttribute("totalReceita", totalReceita);
        model.addAttribute("totalAtivos", totalAtivos);
        model.addAttribute("busca", busca);
        model.addAttribute("statusFiltro", status);
        return "ingressos/lista";
    }

    @GetMapping("/catalogo")
    public String catalogo(Model model) {
        model.addAttribute("eventos", eventoService.listarAtivos());
        return "ingressos/catalogo";
    }

    @GetMapping("/reservar/{eventoId}")
    public String formReserva(@PathVariable String eventoId, Model model) {
        Evento evento = eventoService.buscarPorId(eventoId).orElseThrow();
        model.addAttribute("evento", evento);
        model.addAttribute("ingressoForm", new IngressoForm());
        return "ingressos/reservar";
    }

    @PostMapping("/reservar")
    public String reservar(@ModelAttribute IngressoForm form,
                           @AuthenticationPrincipal UserDetails user,
                           RedirectAttributes ra) {
        try {
            Usuario u = usuarioService.buscarPorUsername(user.getUsername()).orElseThrow();
            form.setNomeComprador(user.getUsername());
            Ingresso ing = ingressoService.reservar(form, u.getId());
            ra.addFlashAttribute("sucesso", "Ingresso reservado com sucesso!");
            return "redirect:/ingressos/" + ing.getId();
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/ingressos/reservar/" + form.getEventoId();
        }
    }

    @GetMapping("/{id}")
    public String detalhe(@PathVariable String id,
                          @AuthenticationPrincipal UserDetails user,
                          Model model) {
        Ingresso ing = ingressoService.buscarPorId(id).orElseThrow();
        Usuario u = usuarioService.buscarPorUsername(user.getUsername()).orElseThrow();
        if (ing.getUsuarioId() != null && !ing.getUsuarioId().equals(u.getId())) return "redirect:/ingressos";
        model.addAttribute("ingresso", ing);
        return "ingressos/detalhe";
    }

    @PostMapping("/{id}/status")
    public String status(@PathVariable String id, @RequestParam String status, RedirectAttributes ra) {
        try {
            ingressoService.atualizarStatus(id, status);
            ra.addFlashAttribute("sucesso", "Status atualizado: " + status);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/ingressos/" + id;
    }

    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable String id, RedirectAttributes ra) {
        ingressoService.deletar(id);
        ra.addFlashAttribute("sucesso", "Ingresso removido.");
        return "redirect:/ingressos";
    }
}
