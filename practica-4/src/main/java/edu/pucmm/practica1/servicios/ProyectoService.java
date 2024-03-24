package edu.pucmm.practica1.servicios;

import edu.pucmm.practica1.entidades.Encryptor;
import edu.pucmm.practica1.entidades.Mock;
import edu.pucmm.practica1.entidades.Proyecto;
import edu.pucmm.practica1.repositorio.MockRepository;
import edu.pucmm.practica1.repositorio.ProyectoRepository;
import edu.pucmm.practica1.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoService {
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockRepository repositorioMock;

    private final Encryptor enc = new Encryptor();

    public List<Proyecto> findAllByUser(String usuario, String rol){
        if(rol.contains("ROLE_ADMIN")){
            return proyectoRepository.findListProyect();
        }
        else {
            return proyectoRepository.findListProyectByUser(usuario);
        }
    }

    public void createProject(Proyecto proyecto,String usuario){
        Proyecto p = proyectoRepository.save(proyecto);
        p.setUsuario(usuarioRepository.findByUsername(usuario));
        p.setPath(enc.encode(p.getId()));
        proyectoRepository.save(p);
    }

    public void updateProject(int id,String nombre,String descripcion){
        Proyecto p = proyectoRepository.findProyectoById(id);
        p.setNombre(nombre);
        p.setDescripcion(descripcion);
        proyectoRepository.save(p);
    }

    public Proyecto findByIdProject(int id){
        return proyectoRepository.findProyectoById(id);
    }

    public void deleteByIdProject(int id){
        Proyecto proyecto = proyectoRepository.findProyectoById(id);
        proyecto.setStatus("DESACTIVE");
        List<Mock> mocks = repositorioMock.findAllMocksByProject(id);

        for(Mock m:mocks){
            m.setEstado("DESACTIVE");
            repositorioMock.save(m);
        }

        proyectoRepository.save(proyecto);
    }
}
