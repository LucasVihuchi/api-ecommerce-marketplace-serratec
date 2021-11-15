package com.grupo4.projetofinalapi.services;

import com.grupo4.projetofinalapi.entities.Usuario;
import com.grupo4.projetofinalapi.exceptions.UsuarioInexistenteException;
import com.grupo4.projetofinalapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomizadoUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String nomeUsuario) {

        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario).
                orElseThrow(() -> new UsuarioInexistenteException("Usuário associado ao nome de usuário '" + nomeUsuario + "' não existe"));

        List<GrantedAuthority> authorits = new ArrayList<>();
        authorits.add(new SimpleGrantedAuthority("ROLE_usuario"));

        return new User(
                usuario.getNomeUsuario(),
                usuario.getSenhaUsuario(),
                authorits
        );

    }
}
