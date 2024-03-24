package edu.pucmm.practica1.entidades;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
public class Proyecto {
    @Id
    @GeneratedValue
    private int id;
    private String nombre;
    @Nullable
    private String descripcion;
    @Nullable
    private String path;
    private String status;
    @OneToOne
    private Usuario usuario;

    public Proyecto() {

    }
}
