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

/** Classe para definir um método customizado de busca de usuário para autenticação
 */
@Service
public class CustomizadoUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /** Método para customizar a busca de usuário no banco de dados e seu nível de autorização
     *
     * @param nomeUsuario nome de usuário fornecido na autenticação
     * @return User com nome de usuário, senha e nível de autorização do usuário.
     */
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
