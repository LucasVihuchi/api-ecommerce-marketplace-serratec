package com.grupo4.projetofinalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.projetofinalapi.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
