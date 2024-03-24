package edu.pucmm.practica1.servicios;

import edu.pucmm.practica1.entidades.Mock;
import edu.pucmm.practica1.entidades.Proyecto;
import edu.pucmm.practica1.entidades.Usuario;
import edu.pucmm.practica1.repositorio.MockRepository;
import edu.pucmm.practica1.repositorio.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MockService {
    @Autowired
    private MockRepository mockRepositorio;
    @Autowired
    private ProyectoRepository proyectoRepository;
    @Value("${token_jwt}")
    private String llaveSecreta;

    public Boolean createMock(Mock mock, int id, int opc){
        Proyecto proyecto = proyectoRepository.findProyectoById(id);
        mock.setProyecto(proyecto);
        mock.setExpirate(this.generateFechaVencimiento(opc));
        if(mockRepositorio.findMockByEndpoint(proyecto.getId(),mock.getEndpoint()) == null){
            mockRepositorio.save(mock);
            return true;
        }

        return false;
    }

    public Date generateFechaVencimiento(int opc){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        switch (opc){
            case 0:
                c.add(Calendar.HOUR,1);
                break;
            case 1:
                c.add(Calendar.DAY_OF_MONTH,1);
                break;
            case 2:
                c.add(Calendar.DAY_OF_MONTH,7);
            case 3:
                c.add(Calendar.MONTH,1);
            case 4:
                c.add(Calendar.YEAR,1);
            default:
                break;
        }

        return c.getTime();
    }
    public Mock findMockById(int id){
        return mockRepositorio.findMockById(id);
    }

    public Mock findMockByJWT(Usuario user, String project_name, String mock_endpoint){
        return mockRepositorio.findMockJWT(user,project_name,mock_endpoint);
    }

    public List<Mock> listMockByProject(int id){
        return mockRepositorio.findAllMocksByProject(id);
    }

    public void deleteMockById(int id){
        Mock mock = mockRepositorio.findMockById(id);
        mock.setEstado("DESACTIVE");
        mockRepositorio.save(mock);
    }
    public void updateMockbyId( int id, String access, String endpoint, String headers, int status,String content,String body,String name,String description,int delay){
        Mock mock = findMockById(id);
        mock.setAccess(access);
        mock.setEndpoint(endpoint);
        mock.setHttp_header(headers);
        mock.setHttp_status(status);
        mock.setContent_type(content);
        mock.setHttp_body(body);
        mock.setName(name);
        mock.setDescription(description);
        mock.setDelay(delay);
        mockRepositorio.save(mock);
    }

}
