package edu.pucmm.practica1.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Usuario implements Serializable {

    @Id
    private String username;
    private String password;
    private boolean activo;
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Rol> roles;

    public Usuario() {

    }


    public String getNameRoles(){
        List<String> resultado = new ArrayList<String>();

        for(Rol r:roles){
            resultado.add(r.getRole());
        }

        return resultado.toString().replace("[","").replace("]","");
    }
}
