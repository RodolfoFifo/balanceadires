package edu.pucmm.practica1.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
public class Rol implements Serializable {
    @Id
    private String role;

    public Rol() {

    }
}