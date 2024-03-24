package edu.pucmm.practica1.repositorio;

import edu.pucmm.practica1.entidades.Mock;
import edu.pucmm.practica1.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockRepository extends JpaRepository<Mock, Integer> {

    @Query("select u from Mock u where u.proyecto.id = ?1")
    List<Mock> findAllMocksByProject(int id_proyecto);

    @Query("SELECT m FROM Mock m WHERE m.id = ?1")
    Mock findMockById(int id);

    @Query("SELECT m FROM Mock m WHERE m.proyecto.usuario = ?1 and m.proyecto.nombre = ?2 and m.endpoint = ?3")
    Mock findMockJWT(Usuario u, String project_name, String mock_endpoint);

    @Query("SELECT m FROM Mock m WHERE m.proyecto.id = ?1 and m.endpoint = ?2")
    Mock findMockByEndpoint(int project_name,String mock_endpoint);


}
