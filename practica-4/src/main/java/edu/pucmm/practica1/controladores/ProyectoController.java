package edu.pucmm.practica1.controladores;

import edu.pucmm.practica1.entidades.Proyecto;
import edu.pucmm.practica1.servicios.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/project")
public class ProyectoController {
    @Value("${server.port}")
    private int puerto;
    @Autowired
    public ProyectoService proyectoService;

    @GetMapping("/listado")
    public String listProject(Model model, @PathParam("redirect") String redirect, HttpSession session){
        Integer contador = (Integer) session.getAttribute("contador");
        if(contador == null){
            contador = 0;
        }
        contador++;
        session.setAttribute("contador", contador);
        String idSesion = session.getId();

        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        String rol = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("proyecto", proyectoService.findAllByUser(usuario,rol));
        model.addAttribute("redirect",redirect);
        model.addAttribute("sesion",idSesion);
        model.addAttribute("contador",contador);
        model.addAttribute("puerto",puerto);
        return "list_project";
    }

    @GetMapping("/generar")
    public String generateProject(Model model, @PathParam("redirect") String redirect){
        model.addAttribute("window","GENERAR_PROJECT");
        model.addAttribute("redirect",redirect);
        return "form_project";
    }

    @GetMapping("/editar")
    public String editeProject(Model model, @PathParam("redirect") String redirect, @PathParam("id") int id){
        model.addAttribute("window","EDITAR_MOCK");
        model.addAttribute("redirect",redirect);
        model.addAttribute("proyecto", proyectoService.findByIdProject(id));
        return "form_project";
    }

    @PostMapping("/generar")
    public String generateProject(@PathParam("redirect") String redirect, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "description") String description){
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        Proyecto proyecto = new Proyecto(0,nombre,description,null,"ACTIVE",null);
        proyectoService.createProject(proyecto,usuario);
        return "redirect:/project/listado?redirect=" + redirect;
    }

    @PostMapping("/editar")
    public String editeProject(@PathParam("id") int id,@PathParam("redirect") String redirect,@RequestParam(name = "nombre") String nombre,@RequestParam(name = "description") String descripcion){
        proyectoService.updateProject(id,nombre,descripcion);
        return "redirect:/project/listado?redirect=" + redirect;
    }

    @GetMapping("/eliminar")
    public String eliminateProject(@PathParam("id") int id,@PathParam("redirect") String redirect){
        proyectoService.deleteByIdProject(id);
        return "redirect:/project/listado?redirect=" + redirect;
    }
}
