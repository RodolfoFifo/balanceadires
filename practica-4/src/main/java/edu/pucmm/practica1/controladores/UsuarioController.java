package edu.pucmm.practica1.controladores;

import edu.pucmm.practica1.servicios.SeguridadService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.Optional;

@Controller
@RequestMapping({"","/usuario"})
public class UsuarioController {
    @Value("${server.port}")
    private int puerto;
    @Autowired
    public SeguridadService seguridadService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(@RequestParam Optional<String> error) {
        return new ModelAndView("login", "error", error);
    }

    @GetMapping("/listado")
    public String listUsuario(Model model, Authentication authentication, HttpSession session){
        Integer contador = (Integer) session.getAttribute("contador");
        if(contador == null){
            contador = 0;
        }
        contador++;
        session.setAttribute("contador", contador);
        String idSesion = session.getId();

        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", seguridadService.loadUsers(usuario));
        model.addAttribute("usuario",usuario);
        model.addAttribute("sesion",idSesion);
        model.addAttribute("contador",contador);
        model.addAttribute("puerto",puerto);
        return "list_user";
    }

    @GetMapping("/generar")
    public String generateUsuario(Model model){
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("usuario",usuario);
        return "form_user";
    }

    @PostMapping("/generar")
    public String generateUsuario(@RequestParam(name = "name") String nombre,@RequestParam(name = "user") String usuario,@RequestParam(name = "pass") String contrasena,@RequestParam(name = "rol") String rol){
        seguridadService.createUsuario(nombre,usuario,contrasena,rol);
        return "redirect:/usuario/listado";
    }

    @GetMapping("/cambiar/role")
    public String changerRole(@PathParam("rol") String rol, @PathParam("user") String user){
        seguridadService.updateRoleInUser(rol,user);
        return "redirect:/usuario/listado";
    }

}
