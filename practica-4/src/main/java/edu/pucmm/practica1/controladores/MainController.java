package edu.pucmm.practica1.controladores;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${server.port}")
    private int puerto;

    @GetMapping({"/","/home"})
    public String home(Model model,HttpSession session){

        Integer contador = (Integer) session.getAttribute("contador");
        if(contador == null){
            contador = 0;
        }
        contador++;
        session.setAttribute("contador", contador);
        String idSesion = session.getId();
        model.addAttribute("sesion",idSesion);
        model.addAttribute("contador",contador);
        model.addAttribute("puerto",puerto);
        return "home";
    }

}
