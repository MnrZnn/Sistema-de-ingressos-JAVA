package com.ingressos.controller;

import com.ingressos.model.*;
import com.ingressos.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final IngressoService ingressoService;
    private final EventoService eventoService;
    private final UsuarioService usuarioService;

    public AdminController(IngressoService ingressoService, EventoService eventoService, UsuarioService usuarioService) {
        this.ingressoService = ingressoService;
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/ingressos")
    public String ingressos(@RequestParam(required = false) String busca,
                            @RequestParam(required = false) String status,
                            Model model) {
        List<Ingresso> ingressos;
        if (busca != null && !busca.isBlank()) ingressos = ingressoService.buscarPorEvento(busca);
        else if (status != null && !status.isBlank()) ingressos = ingressoService.buscarPorStatus(status);
        else ingressos = ingressoService.listarTodos();

        long totalAtivos = ingressos.stream().filter(i -> "RESERVADO".equals(i.getStatus()) || "CONFIRMADO".equals(i.getStatus())).count();

        model.addAttribute("ingressos", ingressos);
        model.addAttribute("totalReceita", ingressoService.calcularTotalReceita());
        model.addAttribute("totalAtivos", totalAtivos);
        model.addAttribute("busca", busca);
        model.addAttribute("statusFiltro", status);
        return "admin/ingressos";
    }

    @GetMapping("/ingressos/novo")
    public String novoForm(Model model) {
        model.addAttribute("ingressoForm", new IngressoForm());
        model.addAttribute("eventos", eventoService.listarTodos());
        return "admin/ingresso-form";
    }

    @PostMapping("/ingressos/novo")
    public String novoSalvar(@ModelAttribute IngressoForm form, RedirectAttributes ra) {
        Ingresso salvo = ingressoService.criar(form);
        ra.addFlashAttribute("sucesso", "Ingresso cadastrado!");
        return "redirect:/admin/ingressos/" + salvo.getId();
    }

    @GetMapping("/ingressos/{id}")
    public String detalhe(@PathVariable String id, Model model) {
        Ingresso ing = ingressoService.buscarPorId(id).orElseThrow();
        model.addAttribute("ingresso", ing);
        return "admin/ingresso-detalhe";
    }

    @PostMapping("/ingressos/{id}/status")
    public String status(@PathVariable String id, @RequestParam String status, RedirectAttributes ra) {
        try {
            ingressoService.atualizarStatus(id, status);
            ra.addFlashAttribute("sucesso", "Status atualizado: " + status);
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/ingressos/" + id;
    }

    @PostMapping("/ingressos/{id}/validar-qr")
    public String validarQr(@PathVariable String id, RedirectAttributes ra) {
        try {
            ingressoService.validarQr(id);
            ra.addFlashAttribute("sucesso", "Ingresso validado e marcado como UTILIZADO.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/admin/ingressos/" + id;
    }

    @PostMapping("/ingressos/{id}/deletar")
    public String deletar(@PathVariable String id, RedirectAttributes ra) {
        ingressoService.deletar(id);
        ra.addFlashAttribute("sucesso", "Ingresso removido.");
        return "redirect:/admin/ingressos";
    }

    // --- Eventos ---

    @GetMapping("/eventos")
    public String eventos(Model model) {
        model.addAttribute("eventos", eventoService.listarTodos());
        return "admin/eventos";
    }

    @GetMapping("/eventos/novo")
    public String eventoNovo(Model model) {
        model.addAttribute("evento", new Evento());
        return "admin/evento-form";
    }

    @PostMapping("/eventos/novo")
    public String eventoSalvar(@ModelAttribute Evento evento, RedirectAttributes ra) {
        eventoService.salvar(evento);
        ra.addFlashAttribute("sucesso", "Evento criado!");
        return "redirect:/admin/eventos";
    }

    @GetMapping("/eventos/{id}/editar")
    public String eventoEditar(@PathVariable String id, Model model) {
        model.addAttribute("evento", eventoService.buscarPorId(id).orElseThrow());
        return "admin/evento-form";
    }

    @PostMapping("/eventos/{id}/editar")
    public String eventoAtualizar(@PathVariable String id, @ModelAttribute Evento ev, RedirectAttributes ra) {
        ev.setId(id);
        eventoService.salvar(ev);
        ra.addFlashAttribute("sucesso", "Evento atualizado.");
        return "redirect:/admin/eventos";
    }

    @PostMapping("/eventos/{id}/deletar")
    public String eventoDeletar(@PathVariable String id, RedirectAttributes ra) {
        eventoService.deletar(id);
        ra.addFlashAttribute("sucesso", "Evento removido.");
        return "redirect:/admin/eventos";
    }

    // --- Relatórios ---

    @GetMapping("/relatorios")
    public String relatorios(@RequestParam(required = false) String eventoId, Model model) {
        model.addAttribute("eventos", eventoService.listarTodos());
        if (eventoId != null && !eventoId.isBlank()) {
            List<Ingresso> ings = ingressoService.listarPorEvento(eventoId);
            model.addAttribute("ingressos", ings);
            model.addAttribute("eventoSelecionado", eventoService.buscarPorId(eventoId).orElse(null));
            model.addAttribute("qtdReservados",  ings.stream().filter(i -> "RESERVADO".equals(i.getStatus())).count());
            model.addAttribute("qtdUtilizados",  ings.stream().filter(i -> "UTILIZADO".equals(i.getStatus())).count());
            model.addAttribute("qtdCancelados",  ings.stream().filter(i -> "CANCELADO".equals(i.getStatus())).count());
            model.addAttribute("receita", ings.stream().filter(i -> !"CANCELADO".equals(i.getStatus())).mapToDouble(Ingresso::calcularValor).sum());
        }
        return "admin/relatorios";
    }
}
