package edu.pucmm.practica1.repositorio;

import edu.pucmm.practica1.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.username <> ?1")
    List<Usuario> findAllUsersByUsername(String usuario);
}