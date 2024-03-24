package edu.pucmm.practica1.controladores;

import edu.pucmm.practica1.entidades.Mock;
import edu.pucmm.practica1.servicios.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Objects;

@Controller
@RequestMapping("/mock")
public class MockController {
    @Autowired
    private MockService mockService;

    @GetMapping("/generar")
    public String generateMock(Model model, @PathParam("id") int id){
        model.addAttribute("id",id);
        return "form_mock";
    }

    @PostMapping("/generar")
    public String generateMock(@PathParam("id") int id, @RequestParam(name="acceso") String access, @RequestParam(name="endpoint") String endpoint, @RequestParam(name="headers") String headers, @RequestParam(name="status") int status, @RequestParam(name="content") String content, @RequestParam(name="body") String body, @RequestParam(name="nombre") String name, @RequestParam(name="description") String description, @RequestParam(name="delay") int delay, @RequestParam(name="expiration") int expiration){
        Mock mock = new Mock(0,access,endpoint,status,content,null,"ACTIVE",headers,body,name,description,delay,null);
        if(mockService.createMock(mock,id,expiration)){
            return "redirect:/home";
        }else {
            return "redirect:/mock/generar?id="+ id;
        }
    }

    @GetMapping("/listado")
    public String listMockView(Model model,@PathParam("id") int id){
        model.addAttribute("mocks", mockService.listMockByProject(id));
        return "list_mock";
    }

    @GetMapping("/editar")
    public String editeMockView(Model model,@PathParam("id") int id){
        model.addAttribute("window","EDITAR_MOCK");
        model.addAttribute("mock", mockService.findMockById(id));
        model.addAttribute("id",id);
        return "form_mock";
    }

    @PostMapping("/editar")
    public String editeMock(@PathParam("id") int id,@RequestParam(name="acceso") String access,@RequestParam(name="endpoint") String endpoint,@RequestParam(name="headers") String headers,@RequestParam(name="status") int status,@RequestParam(name="content") String content,@RequestParam(name="body") String body,@RequestParam(name="nombre") String name, @RequestParam(name="description") String description,@RequestParam(name="delay") int delay){
        mockService.updateMockbyId(id,access,endpoint,headers,status,content,body,name,description,delay);
        return "redirect:/mock/listado?id=" + Objects.requireNonNull(mockService.findMockById(id).getProyecto()).getId();
    }

    @GetMapping("/eliminar")
    public String eliminateMock(@PathParam("id") int id){
        mockService.deleteMockById(id);
        return "redirect:/mock/listado?id=" + id;
    }

}
