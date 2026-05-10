package com.ingressos.service;

import com.ingressos.model.Usuario;
import com.ingressos.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public void registrar(Usuario u) {
        if (repo.findByUsername(u.getUsername()).isPresent()) throw new RuntimeException("Username já em uso");
        u.setPassword(encoder.encode(u.getPassword()));
        if (u.getRole() == null || u.getRole().isBlank()) u.setRole("CLIENTE");
        repo.save(u);
    }

    public Optional<Usuario> buscarPorUsername(String username) { return repo.findByUsername(username); }
    public Optional<Usuario> buscarPorId(String id) { return repo.findById(id); }
    public List<Usuario> listarTodos() { return repo.findAll(); }
    public void deletar(String id) { repo.deleteById(id); }
}
