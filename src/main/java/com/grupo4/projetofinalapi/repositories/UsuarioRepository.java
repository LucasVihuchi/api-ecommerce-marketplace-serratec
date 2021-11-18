package com.grupo4.projetofinalapi.repositories;

import com.grupo4.projetofinalapi.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** Interface repositório para interação com a entidade usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    /** Método para pesquisar usuário pelo seu nome de usuário
     *
     * @param nomeUsuario nome de usuário do usuário a ser pesquisa
     * @return Um optional de Usuário caso verdadeiro. Caso falso, nulo
     */
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);

    /** Método para pesquisar se um email, cpf ou nome de usuário já constam no banco
     *
     * @param email email a ser pesquisado
     * @param cpf cpf a ser pesquisado
     * @param NomeUsuario nome de usuário a ser pesquisado
     * @return List com todos os usuários que correspondem aos dados passados
     */
    List<Usuario> findAllByEmailOrCpfOrNomeUsuario(String email, String cpf, String NomeUsuario);
}
