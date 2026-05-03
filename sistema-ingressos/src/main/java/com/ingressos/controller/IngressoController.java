package com.ingressos.controller;

import com.ingressos.model.Ingresso;
import com.ingressos.model.IngressoForm;
import com.ingressos.service.IngressoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingressos")
public class IngressoController {

    @Autowired
    private IngressoService service;

    /** Listagem com filtros opcionais */
    @GetMapping
    public String listar(@RequestParam(required = false) String busca,
                         @RequestParam(required = false) String status,
                         Model model) {

        List<Ingresso> ingressos;

        if (busca != null && !busca.isBlank()) {
            ingressos = service.buscarPorEvento(busca);
        } else if (status != null && !status.isBlank()) {
            ingressos = service.buscarPorStatus(status);
        } else {
            ingressos = service.listarTodos();
        }

        long totalAtivos = ingressos.stream()
            .filter(i -> "ATIVO".equals(i.getStatus())).count();

        model.addAttribute("ingressos", ingressos);
        model.addAttribute("totalReceita", service.calcularTotalReceita());
        model.addAttribute("totalAtivos", totalAtivos);
        model.addAttribute("busca", busca);
        model.addAttribute("statusFiltro", status);
        return "ingressos/lista";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("ingressoForm", new IngressoForm());
        return "ingressos/form";
    }

    @PostMapping("/novo")
    public String salvar(@Valid @ModelAttribute("ingressoForm") IngressoForm form,
                         BindingResult result,
                         RedirectAttributes redirectAttrs,
                         Model model) {

        if (result.hasErrors()) {
            return "ingressos/form";
        }

        Ingresso salvo = service.createIngresso(form);
        redirectAttrs.addFlashAttribute("sucesso", "Ingresso cadastrado com sucesso!");
        return "redirect:/ingressos/" + salvo.getId();
    }

    /** Detalhe do ingresso */
    @GetMapping("/{id}")
    public String detalhe(@PathVariable String id, Model model) {
        Optional<Ingresso> ingresso = service.buscarPorId(id);
        if (ingresso.isEmpty()) {
            return "redirect:/ingressos";
        }
        model.addAttribute("ingresso", ingresso.get());
        return "ingressos/detalhe";
    }

    /** Atualiza status via POST */
    @PostMapping("/{id}/status")
    public String atualizarStatus(@PathVariable String id,
                                  @RequestParam String status,
                                  RedirectAttributes redirectAttrs) {
        service.atualizarStatus(id, status);
        redirectAttrs.addFlashAttribute("sucesso", "Status atualizado para: " + status);
        return "redirect:/ingressos/" + id;
    }

    /** Remove ingresso */
    @PostMapping("/{id}/deletar")
    public String deletar(@PathVariable String id, RedirectAttributes redirectAttrs) {
        service.deletar(id);
        redirectAttrs.addFlashAttribute("sucesso", "Ingresso removido com sucesso.");
        return "redirect:/ingressos";
    }
}
