package edu.pucmm.practica1.repositorio;

import edu.pucmm.practica1.entidades.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {

    @Query("select p from Proyecto p where p.id = ?1")
    Proyecto findProyectoById(Integer integer);
    @Query("SELECT p FROM Proyecto p WHERE p.status = 'ACTIVE' and p.usuario.username = ?1")
    List<Proyecto> findListProyectByUser(String usuario);

    @Query("SELECT p FROM Proyecto p WHERE p.status = 'ACTIVE'")
    List<Proyecto> findListProyect();
}
