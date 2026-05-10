package com.ingressos.config;

import com.ingressos.model.Evento;
import com.ingressos.model.Usuario;
import com.ingressos.repository.EventoRepository;
import com.ingressos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seed(UsuarioRepository usuarios, EventoRepository eventos, PasswordEncoder enc) {
        return args -> {
          try {
            if (usuarios.findByUsername("admin").isEmpty()) {
                Usuario a = new Usuario();
                a.setUsername("admin"); a.setPassword(enc.encode("admin123")); a.setRole("ADMIN");
                usuarios.save(a);
            }
            if (usuarios.findByUsername("cliente").isEmpty()) {
                Usuario c = new Usuario();
                c.setUsername("cliente"); c.setPassword(enc.encode("cliente123")); c.setRole("CLIENTE");
                usuarios.save(c);
            }
            if (eventos.findByAtivoTrue().isEmpty()) {
                String[][] evs = {
                    {"Show Coldplay", "Music of the Spheres World Tour", "15/08/2025 20:00", "Allianz Parque, SP", "120", "350.0"},
                    {"Lollapalooza", "Festival de música e cultura", "20/09/2025 14:00", "Autódromo Interlagos, SP", "500", "680.0"},
                    {"Rock in Rio", "O maior festival do mundo", "10/10/2025 15:00", "Cidade do Rock, RJ", "300", "450.0"},
                };
                for (String[] e : evs) {
                    Evento ev = new Evento();
                    ev.setNome(e[0]); ev.setDescricao(e[1]); ev.setDataHorario(e[2]);
                    ev.setLocal(e[3]); ev.setIngressosDisponiveis(Integer.parseInt(e[4]));
                    ev.setValorBase(Double.parseDouble(e[5]));
                    eventos.save(ev);
                }
            }
          } catch (Exception e) {
            System.err.println("[DataInitializer] Não foi possível conectar ao MongoDB: " + e.getMessage());
            System.err.println("[DataInitializer] Verifique o IP liberado no MongoDB Atlas (Network Access).");
          }
        };
    }
}
